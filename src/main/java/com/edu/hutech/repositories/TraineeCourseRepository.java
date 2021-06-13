package com.edu.hutech.repositories;

import com.edu.hutech.entities.TraineeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeCourseRepository extends JpaRepository<TraineeCourse, Integer> {

    @Query(name = "select * from demo.trainee_course tc where tc.trainee_id = ?1 and del_flag = 0", nativeQuery = true)
    List<TraineeCourse> findByTraineeId(Integer id);

    @Query(name = "select * from demo.trainee_course tc where del_flag = 0 and tc.course_id = ?1", nativeQuery = true)
    List<TraineeCourse> findByCourseId(Integer courseId);

    @Query(name = "SELECT * FROM demo.trainee_course where course_id = ?1 and trainee_id = ?2 and del_flag = 0", nativeQuery = true)
    TraineeCourse getTraineeCourseByCourseIdAndTraineeId(Integer courseId, Integer traineeId);
}
