package com.org.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long empid;
    private String name;
    private int age;
    private double salary;
    private String email;
    private String workLocation;
    private String platform;
    private String projectName;
    private Long addharNumber;
    private String panNumber;
    private Long mobbileNumber;
}