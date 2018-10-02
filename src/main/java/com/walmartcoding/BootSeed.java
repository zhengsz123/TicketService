package com.walmartcoding;


import com.walmartcoding.service.TicketServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootSeed {

    public static void main (String[] args){
        System.out.println("seed data");
        ApplicationContext context = SpringApplication.run(BootSeed.class, args);
        TicketServiceImpl ticketServiceImpl = context.getBean(TicketServiceImpl.class);
        ticketServiceImpl.insertSeatsData();
        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

}
