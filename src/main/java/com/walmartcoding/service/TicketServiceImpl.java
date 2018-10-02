package com.walmartcoding.service;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.domain.User;
import com.walmartcoding.repository.SeatsRepository;
import com.walmartcoding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TicketServiceImpl implements TicketService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public void insertSeatsData() {
        for (Integer i = 1; i <= 9; i++) {
            for (Integer j = 1; j <= 33; j++) {
                Seat seat = new Seat();
                seat.setRow(i);
                seat.setCol(j);
                seatsRepository.save(seat);
                logger.debug("create seat with id: " + seat.getId());
            }
        }
    }

    @Override
    public int numSeatsAvailable() {
        Integer num = seatsRepository.countSeatsByStatus(SeatStatus.EMPTY.ordinal());
        return num;
    }

    @Override
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
        return holdSeatList;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        List<Seat> reserveredList = seatsRepository.findByUser(userService.findByEmail(customerEmail).getId());
        for(int i = 0 ; i< reserveredList.size();i++){
            Seat seat = reserveredList.get(i);
            seat.setStatus(SeatStatus.RESERVERED.ordinal());
            seatsRepository.save(seat);
        }
        UUID confirmationCode = UUID.randomUUID();
        User user = userService.findByEmail(customerEmail);
        user.setConfirmationCode(confirmationCode.toString());
        userRepository.save(user);
        return confirmationCode.toString();
    }

    @Autowired
    private JmsTemplate jmsTemplate;
    public void sendMessage(String message){
        jmsTemplate.convertAndSend("worker",message);
    }

    @JmsListener(destination = "worker", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message  + ">");
    }
}
