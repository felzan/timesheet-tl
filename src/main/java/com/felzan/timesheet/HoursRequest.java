package com.felzan.timesheet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class HoursRequest {

    private LocalTime hours;
    private LocalDate day;
}
