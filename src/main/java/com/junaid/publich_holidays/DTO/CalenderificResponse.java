package com.junaid.publich_holidays.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CalenderificResponse {
    public List<CalenderificHoliday> holidays;
    public List<CalenderificCountry> countries;
}
