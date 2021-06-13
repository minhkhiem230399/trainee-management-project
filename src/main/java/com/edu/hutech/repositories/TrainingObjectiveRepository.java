package com.edu.hutech.repositories;

import com.edu.hutech.entities.TrainingObjective;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingObjectiveRepository extends JpaRepository<TrainingObjective, Integer> {

//    @Query(name = "select * from demo.training_objective too where too.id_course = ?1 and too.id_trainee = ?2 and del_flag = 0", nativeQuery = true)
//    List<TrainingObjective> getByCourseIdAndTraineeId(Integer courseId, Integer traineeId);
}
