package com.edu.hutech.repositories;

import com.edu.hutech.entities.Trainee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

//    /**
//     *
//     * @param courseId
//     * @return
//     */
//    @Query("SELECT COUNT(t.course) FROM Trainee t WHERE t.course.id = ?1")
//    int countCourseByCourseId(int courseId);
//
    /**
     * Find the list of trainee infor and trainee average score of 1 course
     * @param id is ID of trainee
     * @return list of trainee infor
     */


    /**
     * find the avg score and infor of all trainee
     * @return list of trainee infor with score
     */
    @Query(value = "select * from trainee where del_flag = 0", nativeQuery = true)
    List<Trainee> findScoreByAllTrainee();

    @Query(value = "select * from demo.trainee t where ?1 = (select account from user u where t.user_id = u.id) and del_flag = 0", nativeQuery = true)
    Trainee getTraineeByAccount(String account);


}
