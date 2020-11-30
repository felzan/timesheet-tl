package com.felzan.timesheet;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HoursRequest {

    private LocalTime hours;
    private LocalDate day;
}
