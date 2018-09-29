package com.walmartcoding;


import com.walmartcoding.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCoreBootApplication {

//    @Autowired
//    private SeatService seatService;
    public static void main (String[] args){
        SpringApplication.run(ApiCoreBootApplication.class, args);
//        ApiCoreBootApplication apiCoreBootApplication = new ApiCoreBootApplication();
//        apiCoreBootApplication.seatService.insertSeatsData();
    }


}
