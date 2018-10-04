package com.walmartcoding.service;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.domain.User;
import com.walmartcoding.repository.SeatsRepository;
import com.walmartcoding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TicketServiceImpl implements TicketService<Seat> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Value("#{databaseProperties['db.row']}")
    private Integer row;
    @Value("#{databaseProperties['db.col']}")
    private Integer col;

    @Transactional
    public void insertSeatsData() {
        for (Integer i = 1; i <= row; i++) {
            for (Integer j = 1; j <= col; j++) {
                Seat seat = new Seat();
                seat.setRow(i);
                seat.setCol(j);
                seatsRepository.save(seat);
            }
        }
    }
    @Override
    @Transactional
    public int numSeatsAvailable() {
        Integer num = seatsRepository.countSeatsByStatus(SeatStatus.EMPTY.ordinal());
        return num;
    }

    @Transactional
    public void save(Seat seat){
        seatsRepository.save(seat);
    }

    @Override
    @Transactional
    public List<Seat> findAndHoldSeats(int numSeats, String customerEmail) {
        List<Seat> holdSeatList = new ArrayList<>();
        List<Seat> seatList = seatsRepository.findSeatsByStatus(SeatStatus.EMPTY.ordinal());
        for (int i = 0; i < numSeats; i++) {
            Seat seat = seatList.get(i);
            seat.setStatus(SeatStatus.HOLD.ordinal());
            seat.setUser(userService.findByEmail(customerEmail));
            seatsRepository.save(seat);
            holdSeatList.add(seat);
        }
        jmsTemplate.convertAndSend("worker", customerEmail);
        return holdSeatList;
    }

    @Override
    @Transactional
    public String reserveSeats(int seatHoldId, String customerEmail) {
        List<Seat> reserveredList = seatsRepository.findByUser(userService.findByEmail(customerEmail).getId());
        for (int i = 0; i < reserveredList.size(); i++) {
            Seat seat = reserveredList.get(i);
            seat.setStatus(SeatStatus.RESERVED.ordinal());
            seatsRepository.save(seat);
        }
        UUID confirmationCode = UUID.randomUUID();
        User user = userService.findByEmail(customerEmail);
        user.setConfirmationCode(confirmationCode.toString());
        userRepository.save(user);
        return confirmationCode.toString();
    }

    @JmsListener(destination = "worker")
    @Override
    public void receiveMessage(String message) {
        if (userRepository.findByEmail(message).getConfirmationCode() == null) {
            List<Seat> holdList = seatsRepository.findSeatsByStatus(SeatStatus.HOLD.ordinal());
            for (int i = 0; i < holdList.size(); i++) {
                Seat seat = holdList.get(i);
                seat.setStatus(SeatStatus.EMPTY.ordinal());
                seat.setUser(null);
                seatsRepository.save(seat);
            }
        }

        logger.debug("Received <" + message + ">");
    }
}
