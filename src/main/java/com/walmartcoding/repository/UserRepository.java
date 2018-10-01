package com.walmartcoding.repository;

import com.walmartcoding.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);
}
