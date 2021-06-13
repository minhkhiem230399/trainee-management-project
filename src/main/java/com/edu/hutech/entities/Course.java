package com.edu.hutech.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "open_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String openDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String endDate;

    @Column(name = "duration")
    private int duration;

    @Column(name = "note")
    private String note;

    @Column(name = "plan_count")
    private int planCount;

    @Column(name = "current_count")
    private int currCount;

    @Column(name = "status_progress")
    private String statusProgress;

    @Column(name = "pass")
    private int pass = 0;

    @Column(name = "failed")
    private int failed = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    private List<TraineeCourse> traineeCourses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_subject",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<TrainingObjective> trainingObjectives = new ArrayList<>();

    public void addTraineeCourses(TraineeCourse traineeCourse) {
        traineeCourse.setCourse(this);
        traineeCourses.add(traineeCourse);
    }

//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Issue> issues;


}
