package com.junaid.publich_holidays.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CalenderificHoliday {
    public String name;
    public String description;
    public List<String> type;
    public CalenderificDate date;
}
