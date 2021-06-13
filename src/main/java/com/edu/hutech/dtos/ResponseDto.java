package com.edu.hutech.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ResponseDto {
    Set<Integer> id = new HashSet<>();
}
