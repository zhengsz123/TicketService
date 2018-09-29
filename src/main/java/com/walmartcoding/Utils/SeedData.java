package com.walmartcoding.Utils;

import com.walmartcoding.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;

public class SeedData {
    @Autowired
    private SeatService seatService;

    public static void main(String[] args){
        SeedData seedData = new SeedData();
        seedData.seatService.insertSeatsData();
    }
}
