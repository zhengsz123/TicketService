package com.walmartcoding.controller;

import com.walmartcoding.domain.User;
import com.walmartcoding.repository.UserRepository;
import com.walmartcoding.service.TicketService;
import com.walmartcoding.service.TicketServiceImpl;
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
    @ResponseBody
    public void getSeatStatus(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(value ="/confirmed",method = RequestMethod.PATCH)
    @ResponseBody
    public String confirmReservation(@RequestParam("numOfSeats") int numOfSeats, @RequestParam("email") String email ){
        String confirmation = "Thanks for your purchase, your confirmation code is "+ticketService.reserveSeats(numOfSeats,email);
        return confirmation;
    }
}
