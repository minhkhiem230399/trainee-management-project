package com.edu.hutech.services.core;

import com.edu.hutech.dtos.TraineeScoreDto;
import com.edu.hutech.entities.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface TraineeService extends IService<Trainee> {


    /*Page<TraineeScoreDto> findPaginated(int pageNo, int idCourse);*/

}
