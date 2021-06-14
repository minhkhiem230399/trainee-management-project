package com.edu.hutech.services.implementation;

import java.util.List;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.repositories.TrainerRepository;
import com.edu.hutech.repositories.UserRepository;
import com.edu.hutech.services.core.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    /**
     * update trainer
     *
     * @param trainer
     */
    @Override
    public void update(Trainer trainer) {
        Trainer trainerInDB = trainerRepository.getOne(trainer.getId());
        trainerInDB.setName(trainer.getName());
        trainerInDB.setEmail(trainer.getEmail());
        trainerInDB.setTelPhone(trainer.getTelPhone());
        trainerInDB.setBirthDay(trainer.getBirthDay());
        trainerInDB.setAddress(trainer.getAddress());
        trainerInDB.getUser().setAccount(trainer.getEmail().substring(0, trainer.getEmail().indexOf("@")));
        trainerRepository.save(trainerInDB);
    }

    /**
     * delete trainer
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        Trainer trainer = trainerRepository.getOne(id);
        trainer.setDelFlag(1);
        trainer.getUser().setDelFlag(1);
        for (Course course : trainer.getCourseList()) {
            course.setDelFlag(1);
        }
        trainerRepository.save(trainer);
    }

    @Override
    public Trainer findById(Integer id) {
        return trainerRepository.getOne(id);
    }

    @Override
    public List<Trainer> getAll() {
        List<Trainer> list = trainerRepository.findAll();
        list.removeIf(trainer -> trainer.getDelFlag() == 1);
        return list;
    }
}
