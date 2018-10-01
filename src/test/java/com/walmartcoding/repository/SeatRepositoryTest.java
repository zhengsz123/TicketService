package com.walmartcoding.repository;

import com.walmartcoding.config.AppConfig;
import com.walmartcoding.config.DatabaseConfig;
import com.walmartcoding.domain.SeatStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;


@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class,DatabaseConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("unit")
public class SeatRepositoryTest {

    @Autowired
    private SeatsRepository seatsRepository;

    @Test
    @Transactional
    public void findSeatsByStatusTest(){
        Integer result = 33*9;
        Integer testSeats = seatsRepository.countSeatsByStatus(SeatStatus.EMPTY.ordinal());
        assertEquals(result,testSeats);
    }
}
