package com.edu.hutech.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "training_objective")
public class TrainingObjective extends BaseEntity implements Serializable {


    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingObjectives")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingObjective", fetch = FetchType.LAZY)
    private List<TraineeSubject> traineeSubjects = new ArrayList<>();

    public void addCourse(TraineeSubject traineeSubject) {
        traineeSubject.setTrainingObjective(this);
        traineeSubjects.add(traineeSubject);
    }
}
