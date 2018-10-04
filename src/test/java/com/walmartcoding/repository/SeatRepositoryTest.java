package com.walmartcoding.repository;

import com.walmartcoding.config.AppConfig;
import com.walmartcoding.config.DatabaseConfig;
import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.domain.User;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class,DatabaseConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SeatRepositoryTest {
    @Autowired
    private SeatsRepository seatsRepository;

    @Test
    @Transactional
    public void findSeatsByStatusTest(){
        Seat seat = new Seat();
        seat.setRow(1);
        seat.setCol(1);
        seat.setStatus(SeatStatus.EMPTY.ordinal());
        seatsRepository.save(seat);
        List<Seat> seatList = seatsRepository.findSeatsByStatus(SeatStatus.EMPTY.ordinal());
        assertEquals(SeatStatus.EMPTY.ordinal(),seatList.get(0).getStatus().intValue());
    }
}
