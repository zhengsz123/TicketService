package com.walmartcoding.controller;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.service.TicketService;
import com.walmartcoding.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/seat"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class SeatController {
    @Autowired
    private TicketService ticketServiceImpl;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public String getSeatStatus() {
        int seatsAvailable = ticketServiceImpl.numSeatsAvailable();
        String result = "There are " + Integer.toString(seatsAvailable) + " seats available to pick.";
        return result;
    }

    @RequestMapping(value = "/status", method = RequestMethod.PATCH)
    @ResponseBody
    public List<Seat> updateStatus(@RequestParam("numOfSeats") int numOfSeats, @RequestParam("email") String email) {
        List<Seat> reservedSeats = ticketServiceImpl.findAndHoldSeats(numOfSeats, email);
        return reservedSeats;
    }
}
