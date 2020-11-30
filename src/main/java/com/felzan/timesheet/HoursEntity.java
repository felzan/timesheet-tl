package com.felzan.timesheet;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HoursEntity {

    private Integer id;
    private LocalTime hours;
    private LocalDate day;
    private Integer projectId;
    private String username;

}