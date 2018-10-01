package com.walmartcoding.controller;

import com.walmartcoding.domain.User;
import com.walmartcoding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/user"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public void getSeatStatus(@RequestBody User user) {
        userRepository.save(user);
    }
}
