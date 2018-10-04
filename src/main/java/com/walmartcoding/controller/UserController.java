package com.walmartcoding.controller;

import com.walmartcoding.domain.User;
import com.walmartcoding.repository.UserRepository;
import com.walmartcoding.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/user"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void getSeatStatus(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(value ="/confirmed",method = RequestMethod.PATCH)
    public User confirmReservation(@RequestParam("numOfSeats") int numOfSeats, @RequestParam("email") String email ){
       ticketService.reserveSeats(numOfSeats,email);
       User user = userRepository.findByEmail(email);
       return user;
    }
}
