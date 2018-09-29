package com.walmartcoding.service;

import com.walmartcoding.domain.Seat;
import com.walmartcoding.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private SeatsRepository seatsRepository;

    public void insertSeatsData(){
    for(Integer i = 1; i<=9;i++){
        for(Integer j = 1;j<=33;j++){
            Seat seat = new Seat();
            seat.setRow(i);
            seat.setCol(j);
            seatsRepository.save(seat);
        }
    }
    }
}
