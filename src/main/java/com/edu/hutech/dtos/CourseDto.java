package com.edu.hutech.dtos;

import com.edu.hutech.entities.Course;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDto {

    private Integer id;

    private String name;

    private String openDate;

    private String endDate;

    private Integer duration;

    private String note;

    private Integer planCount;

    private Integer currCount;

    private Integer status;

    private TrainerDto trainerDto;

    private Integer trainerId;

    private String statusProgress;

    public CourseDto(Course entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.openDate = entity.getOpenDate();
        this.endDate = entity.getEndDate();
        this.duration = entity.getDuration();
        this.note = entity.getNote();
        this.planCount = entity.getPlanCount();
        this.currCount = entity.getCurrCount();
        this.status = entity.getStatus();
        this.trainerDto = new TrainerDto(entity.getTrainer());
        this.trainerId = entity.getTrainer().getId();
        this.statusProgress = entity.getStatusProgress();
    }
}
