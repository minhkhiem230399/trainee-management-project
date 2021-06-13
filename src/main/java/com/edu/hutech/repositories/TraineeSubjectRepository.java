package com.edu.hutech.repositories;

import com.edu.hutech.entities.TraineeSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeSubjectRepository extends JpaRepository<TraineeSubject, Integer> {

}
