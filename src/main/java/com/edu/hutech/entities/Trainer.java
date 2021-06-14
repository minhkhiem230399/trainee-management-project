package com.edu.hutech.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "trainer")
public class Trainer extends BaseEntity implements Serializable {

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courseList;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tel_phone")
    private String telPhone;

    @Column(name = "photo")
    private String photo;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_day")
    private String birthDay;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
