package com.edu.hutech.entities;

import com.edu.hutech.utils.data.TypeAttendance;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_person", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "type_attendance")
    private TypeAttendance typeAttendance;

    @Column(name = "date")
    private Date date;

    @Column(name = "note")
    private String note;

}
