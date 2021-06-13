package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.TrainingObjective;
import com.edu.hutech.repositories.TrainingObjectiveRepository;
import com.edu.hutech.services.core.TrainingObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingObjectServiceIpml implements TrainingObjectService {

    @Autowired
    TrainingObjectiveRepository trainingObjectiveRepository;




    @Override
    public void save(TrainingObjective trainingObjective) {
        trainingObjectiveRepository.save(trainingObjective);
    }

    @Override
    public void update(TrainingObjective trainingObjective) {

    }

    @Override
    public void delete(Integer theId) {

    }

    @Override
    public TrainingObjective findById(Integer id) {
        List<TrainingObjective> trainingObjectiveList = getAll();
        for (TrainingObjective trainingObjective : trainingObjectiveList){
            if(trainingObjective.getId().equals(id)){
                return trainingObjective;
            }
        }
        return null;
    }

    @Override
    public List<TrainingObjective> getAll() {
        List<TrainingObjective> trainingObjectives = trainingObjectiveRepository.findAll();
        trainingObjectives.removeIf(trainingObjective -> trainingObjective.getDelFlag() == 1 );
        return trainingObjectives;
    }

    public List<TrainingObjective> getAllName(String name) {
        List<TrainingObjective> nameTraining = new ArrayList<>();
       for (TrainingObjective trainingObjective : getAll()){
           if(trainingObjective.getName().equals(name)){
               nameTraining.add(trainingObjective);
           }
       }
        return nameTraining;
    }

}
