package com.junaid.publich_holidays.DTO;

import com.fasterxml.jackson.annotation.JsonKey;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CalenderificCountry {
    @SerializedName(("country_name"))
    public String name;

    @SerializedName("iso-3166")
    public String iso_3166;
}
