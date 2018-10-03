package com.walmartcoding.service;

import com.walmartcoding.domain.Seat;

import java.util.List;

public interface TicketService<T> {

         void insertSeatsData();
         void save(T t);
        /**
         * The number of seats in the venue that are neither held nor reserved
         *
         * @return the number of tickets available in the venue
         */
        int numSeatsAvailable();
        /**
         * Find and hold the best available seats for a customer
         *
         * @param numSeats the number of seats to find and hold
         * @param customerEmail unique identifier for the customer
         * @return a SeatHold object identifying the specific seats and related
        information
         */
        List<Seat> findAndHoldSeats(int numSeats, String customerEmail);
        /**
         * Commit seats held for a specific customer
         *
         * @param seatHoldId the seat hold identifier
         * @param customerEmail the email address of the customer to which the
        seat hold is assigned
         * @return a reservation confirmation code
         */
        String reserveSeats(int seatHoldId, String customerEmail);

        void receiveMessage(String message);

}
