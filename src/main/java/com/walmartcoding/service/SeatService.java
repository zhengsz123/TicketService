package com.walmartcoding.service;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.repository.SeatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SeatService implements TicketService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private UserService userService;

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
        List<Seat> reservedSeatList = new ArrayList<>();
        List<Seat> seatList = seatsRepository.findSeatsByStatus(SeatStatus.EMPTY.ordinal());
        for (int i = 0; i < numSeats; i++) {
            Seat seat = seatList.get(i);
            seat.setStatus(SeatStatus.RESERVERED.ordinal());
            seat.setUser(userService.findByEmail(customerEmail));
            seatsRepository.save(seat);
            reservedSeatList.add(seat);
        }
        return reservedSeatList;
    }

    /**
     * Commit seats held for a specific customer
     *
     * @param seatHoldId    the seat hold identifier
     * @param customerEmail the email address of the customer to which the
     *                      seat hold is assigned
     * @return a reservation confirmation code
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {

        return customerEmail;
    }

}
