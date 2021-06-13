package com.edu.hutech.entities;

import com.edu.hutech.utils.data.Rate;
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
@Table(name = "certificate")
public class Certificates extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainee_id", nullable = false, referencedColumnName = "Id")
    private Trainee trainee;

    @Column(name = "final_grade")
    private Rate finalGrade;

    @Column(name = "completion_level")
    private String completionLevel;

    @Column(name = "provider")
    private String provider;

    @Column(name = "group_of")
    private String group;

    @Column(name = "sub_group")
    private String subGroup;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

}
