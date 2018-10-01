package com.walmartcoding.controller;

import com.walmartcoding.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/seat"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class SeatController {
    @Autowired
    SeatService seatService;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public String getSeatStatus() {
    int seatsAvailable = seatService.numSeatsAvailable();
    String result = "There are " + Integer.toString(seatsAvailable) + " available to choose.";
    return result;
    }
}
