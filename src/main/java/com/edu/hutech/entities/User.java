package com.edu.hutech.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails, Serializable {

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(name="account")
    private String account;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    @Column(nullable = true, length = 64)
    private String photos;

    @Column(name = "password")
    private String password;


    @OneToOne(mappedBy = "user")
    private Trainer trainer;

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    @OneToOne(mappedBy = "user")
    private Trainee trainee;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        if(this.getRoles().getAuthority().equals("ROLE_ADMIN") ){
            return "Administrator";
        }
        if(this.getRoles().getAuthority().equals("ROLE_TRAINER")){
            return this.getTrainer().getName();
        }
        return this.getTrainee().getName();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return roles.get(0);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public User getUser() {
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
