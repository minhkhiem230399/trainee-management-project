package com.edu.hutech.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "trainee")
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tel_phone")
    private String telPhone;

    @Column(name = "branch")
    private String branch;

    @Column(name = "parent_department")
    private String parentDepartment;

    @Column(name = "rec_intervie_date")
    private Date recInterviewDate;

    @Column(name = "rec_interview_status")
    private String recInterviewStatus;

    @Column(name = "note")
    private String note;


    @Column(name = "university")
    private String university;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_day")
    private String birthDay;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "trainee", fetch = FetchType.LAZY)
    private List<TraineeSubject> traineeSubjects = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
