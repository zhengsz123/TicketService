package com.walmartcoding.controller;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api/seat"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class SeatController {
    @Autowired
    SeatService seatService;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public String getSeatStatus() {
    int seatsAvailable = seatService.numSeatsAvailable();
    String result = "There are " + Integer.toString(seatsAvailable) + " seats available to pick.";
    return result;
    }



    @RequestMapping(value = "/status", method = RequestMethod.PATCH)
    @ResponseBody
    public List<Seat> updateStatus(@RequestParam("numOfSeats") int numOfSeats, @RequestParam("email") String email) {
        List<Seat> reservedSeats =seatService.findAndHoldSeats(numOfSeats,email);
        return reservedSeats;
    }
}
