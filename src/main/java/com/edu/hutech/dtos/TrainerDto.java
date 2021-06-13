package com.edu.hutech.dtos;
import com.edu.hutech.entities.Trainer;

public class TrainerDto {

    public Integer id;

    public String name;

    public String email;

    public String telNumber;

    public TrainerDto(Trainer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.telNumber = entity.getTelPhone();
    }
}
