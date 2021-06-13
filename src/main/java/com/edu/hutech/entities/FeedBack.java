package com.edu.hutech.entities;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "feedback")
public class FeedBack extends BaseEntity{

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idto", referencedColumnName = "id")
//    private TrainingObjective trainingObjective;

    @Column(name = "consult_date")
    private Date consultDate;

    @Column(name = "feed_back_score")
    private float feedBackScore;

//    @OneToMany(mappedBy = "feedback" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<QuestionFeedBack> questionList;

}
