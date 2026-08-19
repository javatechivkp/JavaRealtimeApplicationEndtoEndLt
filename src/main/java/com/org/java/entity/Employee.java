package com.org.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
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
