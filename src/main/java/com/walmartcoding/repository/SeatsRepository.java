package com.walmartcoding.repository;

import com.walmartcoding.domain.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends CrudRepository<Seat,Integer> {
}
