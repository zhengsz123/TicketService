package com.walmartcoding.service;


import com.walmartcoding.config.AppConfig;
import com.walmartcoding.config.DatabaseConfig;
import com.walmartcoding.domain.Seat;
import com.walmartcoding.domain.SeatStatus;
import com.walmartcoding.domain.User;
import com.walmartcoding.repository.SeatsRepository;
import com.walmartcoding.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {AppConfig.class, DatabaseConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@ActiveProfiles("unit")
public class SeatServiceTest {
    @Value("#{databaseProperties['db.row']}")
    private Integer row;
    @Value("#{databaseProperties['db.col']}")
    private Integer col;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private JmsTemplate jmsTemplate;
    private String emailTest="test@test.com";
    private String firstNameTest = "John";
    private String lastNameTest="Doe";
    @Test
    @Transactional
    public void insertSeatsDataTest() {
        ticketService.insertSeatsData();
        int seatsNum = seatsRepository.countSeatsByStatus(SeatStatus.EMPTY.ordinal());
        assertEquals(col*row, seatsNum);
    }

    @Test
    @Transactional
    public void findAndHoldSeatsTest() {
        Seat seat = new Seat();
        User user = new User();
        user.setEmail(emailTest);
        user.setFirstName(firstNameTest);
        user.setLastName(lastNameTest);
        seat.setRow(1);
        seat.setCol(1);
        seat.setStatus(SeatStatus.EMPTY.ordinal());
        seat.setUser(user);
        userRepository.save(user);
        seatsRepository.save(seat);
        List<Seat> seatList = ticketService.findAndHoldSeats(1, emailTest);
        assertEquals(SeatStatus.HOLD.ordinal(), seatList.get(0).getStatus().intValue());
        verify(jmsTemplate, times(1)).convertAndSend(anyString(), anyString());
    }

    @Test
    @Transactional
    public void reserveSeatsTest() {
        Seat seat = new Seat();
        User user = new User();
        user.setEmail(emailTest);
        user.setFirstName(firstNameTest);
        user.setLastName(lastNameTest);
        seat.setRow(1);
        seat.setCol(1);
        seat.setStatus(SeatStatus.HOLD.ordinal());
        seat.setUser(user);
        userRepository.save(user);
        seatsRepository.save(seat);
        ticketService.reserveSeats(1, emailTest);
        assertEquals(SeatStatus.RESERVED.ordinal(), seatsRepository.findByUser(userRepository.findByEmail(emailTest).
                getId()).get(0).getStatus().intValue());
        assertNotNull(userRepository.findByEmail(emailTest).getConfirmationCode());
    }

    @Test
    @Transactional
    public void receiveMessageTest() {
        Seat seat = new Seat();
        User user = new User();
        user.setEmail(emailTest);
        user.setFirstName(firstNameTest);
        user.setLastName(lastNameTest);
        seat.setRow(1);
        seat.setCol(1);
        seat.setStatus(SeatStatus.HOLD.ordinal());
        seat.setUser(user);
        userRepository.save(user);
        seatsRepository.save(seat);
        ticketService.receiveMessage(emailTest);
        assertEquals(SeatStatus.EMPTY.ordinal(), seatsRepository.findAll().get(0).getStatus().intValue());
    }

}
