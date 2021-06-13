package com.edu.hutech.dtos;

public class TraineeDto {

    public int id;

    public String name;

    public String account;

    public float score;

    public String email;

    public String university;

    public TraineeDto(int id, String name, String account, float score, String email, String university) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.score = score;
        this.email = email;
        this.university = university;
    }

}
