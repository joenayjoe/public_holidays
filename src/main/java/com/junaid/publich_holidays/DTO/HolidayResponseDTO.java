package com.junaid.publich_holidays.DTO;

import lombok.Data;

import java.util.List;

@Data
public class HolidayResponseDTO {
    public String name;
    public String description;
    public String date;
    public List<String> type;
}
