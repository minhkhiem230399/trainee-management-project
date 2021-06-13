package com.edu.hutech.services.core;

import com.edu.hutech.dtos.CourseDto;
import com.edu.hutech.entities.Course;

import com.edu.hutech.functiondto.CourseSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CourseService extends IService<Course> {
    Page<CourseDto> searchByDto(CourseSearchDto dto, Integer idTrainer, Integer idTrainee);


}
