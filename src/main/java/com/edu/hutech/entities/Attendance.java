package com.edu.hutech.entities;

import com.edu.hutech.utils.data.TypeAttendance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_person", nullable = true, referencedColumnName = "id")
    private TraineeCourse traineeCourse;

    @Column(name = "status_attendance")
    private Integer statusAttendance = 0;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
}
