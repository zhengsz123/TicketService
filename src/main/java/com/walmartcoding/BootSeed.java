package com.walmartcoding;


import com.walmartcoding.Utils.SeedData;
import com.walmartcoding.service.TicketServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="com.walmartcoding")
public class BootSeed {

    public static void main (String[] args){
        System.out.println("seed data");
        ApplicationContext context = SpringApplication.run(BootSeed.class, args);
        SeedData seedData = context.getBean(SeedData.class);
        seedData.insertSeatsData();
        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

}
