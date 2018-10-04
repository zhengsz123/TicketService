package com.walmartcoding;
import com.walmartcoding.domain.Seat;
import com.walmartcoding.service.TicketService;
import com.walmartcoding.service.TicketServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="com.walmartcoding")
public class BootSeed {

    public static void main (String[] args){
        ApplicationContext context = SpringApplication.run(BootSeed.class, args);
        TicketService<Seat> ticketService = context.getBean(TicketService.class);
        ticketService.insertSeatsData();
        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

}
