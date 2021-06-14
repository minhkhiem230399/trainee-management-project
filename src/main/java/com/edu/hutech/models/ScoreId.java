package com.edu.hutech.models;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TrainingObjective;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.CascadeType;

@Data
@Embeddable
public class ScoreId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.ALL)
    private Trainee trainee;

    @ManyToOne(cascade = CascadeType.ALL)
    private TrainingObjective trainingObjective;

}
