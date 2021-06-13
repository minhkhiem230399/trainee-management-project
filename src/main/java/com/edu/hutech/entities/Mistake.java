package com.edu.hutech.entities;

import lombok.Data;

import java.util.Date;

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
@Table(name = "mistake")
public class Mistake extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String content;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_trainee", nullable = false, referencedColumnName = "id")
    private Trainee trainee;

    @Column(name = "date")
    private Date date;

}
