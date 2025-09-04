package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    private String name;
    private Long categoryId;
    private Integer pageNo;
    private Integer pageSize;
    private String sortParam;
}
