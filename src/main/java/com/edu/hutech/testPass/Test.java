package com.edu.hutech.testPass;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Boolean result = encoder.matches("123456789", "$2a$10$DFA4/Fq25m06qAL6I1SuIuQbt3.NYQepsRZfHYXUzqSmcHakT.IkG");
        System.out.println(result);
        /*List<SensorType> sensorTypeList = new List<SensorType>() ;
        for (Sensor sensor : sensors)
        {
            if(sensor.sensorType.equals("Cat")){
                sensorTypeList.add(sensor.sensorType);
            }
        }*/
    }
}
