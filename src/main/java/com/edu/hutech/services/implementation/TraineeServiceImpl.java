package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeCourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.services.core.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TraineeCourseRepository traineeCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(Trainee trainee) {
        traineeRepository.save(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        Trainee traineeInDB = traineeRepository.getOne(trainee.getId());
        traineeInDB.setName(trainee.getName());
        traineeInDB.setEmail(trainee.getEmail());
        traineeInDB.setTelPhone(trainee.getTelPhone());
        traineeInDB.setUniversity(trainee.getUniversity());
        traineeInDB.setAddress(trainee.getAddress());
        traineeInDB.setBirthDay(trainee.getBirthDay());
        traineeInDB.getUser().setAccount(trainee.getEmail().substring(0, trainee.getEmail().indexOf("@")));
        traineeRepository.save(traineeInDB);
    }

    @Override
    public void delete(Integer id) {
        Trainee trainee = traineeRepository.getOne(id);
        trainee.setDelFlag(1);

        List<TraineeCourse> traineeCourses = traineeCourseRepository.findByTraineeId(id);
        Set<Integer> listCourseId = new HashSet<>();
        for (TraineeCourse traineeCourse : traineeCourses){
            traineeCourse.setDelFlag(1);
            traineeCourseRepository.save(traineeCourse);
            listCourseId.add(traineeCourse.getCourse().getId());
        }
        for (Integer f : listCourseId) {
            Course course = courseRepository.getOne(f);
            course.setCurrCount(course.getCurrCount() - 1);
            courseRepository.save(course);
        }
        traineeRepository.save(trainee);
    }

    @Override
    public Trainee findById(Integer id) {
        return traineeRepository.getOne(id);
    }

    @Override
    public List<Trainee> getAll() {
        List<Trainee> list = new ArrayList<>();
        for (Trainee trainee : traineeRepository.findAll()){
            if(trainee.getDelFlag() == 0){
                list.add(trainee);
            }
        }
        return list;
    }

    public Trainee getTraineeByAccount(String account){
        if(traineeRepository.getTraineeByAccount(account) != null){
            return traineeRepository.getTraineeByAccount(account);
        }
        return null;
    }


}
