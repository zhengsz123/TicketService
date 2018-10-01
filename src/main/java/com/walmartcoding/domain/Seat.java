package com.walmartcoding.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seats_id_seq")
    @SequenceGenerator(name = "seats_id_seq", sequenceName = "seats_id_seq",allocationSize = 1)
    private long id;

    @Column(name = "col")
    @NotNull
    private Integer col;
    @Column(name = "row")
    @NotNull
    private Integer row;
    @Column(name = "status")
    private Integer status=SeatStatus.EMPTY.ordinal();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public long getId() {
        return id;
    }

    public Integer getCol() {
        return col;
    }

    public Integer getRow() {
        return row;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
