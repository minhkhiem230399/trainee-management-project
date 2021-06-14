package com.edu.hutech.repositories;

import com.edu.hutech.entities.Trainee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

    @Query(value = "select * from trainee where del_flag = 0", nativeQuery = true)
    List<Trainee> findScoreByAllTrainee();

    @Query(value = "select * from demo.trainee t where ?1 = (select account from user u where t.user_id = u.id) and del_flag = 0", nativeQuery = true)
    Trainee getTraineeByAccount(String account);


}
