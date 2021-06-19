package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.repositories.TraineeCourseRepository;
import com.edu.hutech.repositories.TrainingObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeCourseService {

    @Autowired
    private TraineeCourseRepository traineeCourseRepository;

    @Autowired
    private TrainingObjectiveRepository trainingObjectiveRepository;

    public Double getScoreByTraineeId(Integer courseId, Integer traineeId) {
        TraineeCourse traineeCourse = traineeCourseRepository.getTraineeCourseByCourseIdAndTraineeId(courseId, traineeId);

        if (traineeCourse != null) {
            return traineeCourse.getScore();
        }
        return (double) 0;
    }

    public boolean checkExistTrainee(Integer courseId, Integer traineeId) {
        return traineeCourseRepository.getTraineeCourseByCourseIdAndTraineeId(courseId, traineeId) != null;
    }

    public List<TraineeCourse> getTraineeCourseByCourseId(Integer id) {
        List<TraineeCourse> traineeCourse = traineeCourseRepository.findByCourseId(id);
        traineeCourse.removeIf(traineeCourse1 -> traineeCourse1.getDelFlag() == 1);
        return traineeCourse;
    }

    public TraineeCourse getByTCourseIdAndTraineeId(Integer courseId, Integer traineeId) {
        return traineeCourseRepository.getTraineeCourseByCourseIdAndTraineeId(courseId, traineeId);
    }

    public TraineeCourse findById(Integer id) {
        return traineeCourseRepository.getOne(id);
    }

}
