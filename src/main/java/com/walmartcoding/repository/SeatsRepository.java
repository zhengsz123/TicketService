package com.walmartcoding.repository;

import com.walmartcoding.domain.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends CrudRepository<Seat, Long> {
    Integer countSeatsByStatus(Integer seatStatus);

    List<Seat> findSeatsByStatus(Integer seatStatus);

    @Query ("select a from Seat a where a.user.id = ?1")
    List<Seat> findByUser(Long userId);
}
