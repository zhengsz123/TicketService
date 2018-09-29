package com.walmartcoding;


import com.walmartcoding.service.SeatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootSeed {

    public static void main (String[] args){
        System.out.println("seed data");
        ApplicationContext context = SpringApplication.run(BootSeed.class, args);
        SeatService seatService = context.getBean(SeatService.class);
        seatService.insertSeatsData();
        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

}
