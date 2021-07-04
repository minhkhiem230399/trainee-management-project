package com.edu.hutech;

import com.edu.hutech.controllers.TrainerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class TraineeProjectApplication {

	public static void main(String[] args) {
		new File(TrainerController.uploadDirection).mkdir();
		SpringApplication.run(TraineeProjectApplication.class, args);
	}

}
