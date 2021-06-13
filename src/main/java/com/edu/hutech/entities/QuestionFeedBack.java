package com.edu.hutech.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "question_feedback")
public class QuestionFeedBack extends BaseEntity{

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_feedback", referencedColumnName = "id")
//    private FeedBack feedBack;

    @Column(name = "group_of_question")
    private String groupOfQuestion;

    @Column(name = "topic")
    private String topic;

    @Column(name = "question_content")
    private String content;

    @Column(name = "score")
    private float score;

}
