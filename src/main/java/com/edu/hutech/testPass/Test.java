package com.edu.hutech.testPass;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("admin");
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
