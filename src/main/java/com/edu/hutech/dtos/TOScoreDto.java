package com.edu.hutech.dtos;

import lombok.Data;

@Data
public class TOScoreDto {
    private String name;

    private double score;

    public double getScore() {
        double scale = Math.pow(10, 1);
        return Math.round(score * scale) / scale;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
