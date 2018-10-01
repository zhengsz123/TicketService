package com.walmartcoding.service;

import com.walmartcoding.TicketService;
import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.domain.User;
import com.walmartcoding.repository.SeatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SeatService implements TicketService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SeatsRepository seatsRepository;

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

    /**
     * The number of seats in the venue that are neither held nor reserved
     *
     * @return the number of tickets available in the venue
     */
    @Override
    public int numSeatsAvailable() {
        Integer num = seatsRepository.countSeatsByStatus(SeatStatus.EMPTY.ordinal());
        return num;
    }

    /**
     * Find and hold the best available seats for a customer
     *
     * @param numSeats      the number of seats to find and hold
     * @param customerEmail unique identifier for the customer
     * @return a SeatHold object identifying the specific seats and related
     * information
     */
    @Override
    public User findAndHoldSeats(int numSeats, String customerEmail) {
        User user = new User();
        return user;
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
