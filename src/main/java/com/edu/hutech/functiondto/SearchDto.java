package com.edu.hutech.functiondto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private int pageIndex;
    private int pageSize;
    private String text;

}
