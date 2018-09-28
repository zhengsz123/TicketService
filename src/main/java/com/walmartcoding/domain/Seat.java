package com.walmartcoding.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
public class Seat {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seats_id_seq")
    @SequenceGenerator(name = "seats_id_seq", sequenceName = "seats_id_seq")
    private long id;
    @Column(name = "col")
    private int col;
    @Column(name = "row")
    private int row;
    @Column(name = "status")
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "users_id")
    private User user;

    public long getId() {
        return id;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
