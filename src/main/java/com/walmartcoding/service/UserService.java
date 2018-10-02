package com.walmartcoding.service;

import com.walmartcoding.domain.User;
import com.walmartcoding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
}
