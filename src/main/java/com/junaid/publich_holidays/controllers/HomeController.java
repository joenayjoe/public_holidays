package com.junaid.publich_holidays.controllers;

import com.google.gson.Gson;
import com.junaid.publich_holidays.DTO.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String API_KEY = "56ce3aa3fe975e4deced3ab05079c9892572e0e8";
    private List<CalenderificCountry> countries = new ArrayList<>();

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("countries", getSupportedCountries());
        model.addAttribute("requestData", new HolidayRequestDTO());
        return "home/index";
    }

    @PostMapping(value = "public_holidays", consumes = {"application/x-www-form-urlencoded"})
    public String getHolidays(@ModelAttribute HolidayRequestDTO requestData, Model model){
        String uri = "https://calendarific.com/api/v2/holidays?api_key="+API_KEY+"&country="+requestData.country+"&year="+ LocalDate.now().getYear();
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        List<HolidayResponseDTO> holidays = new ArrayList<>();
        try{
            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder().uri(URI.create(uri)).header("Accept", "application/json").GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Calenderific calenderific = new Gson().fromJson(response.body(), Calenderific.class);
            for(CalenderificHoliday holiday : calenderific.response.holidays) {
                HolidayResponseDTO dto = new HolidayResponseDTO();
                dto.name = holiday.name;
                dto.description = holiday.description;
                dto.date = holiday.date.iso;
                dto.type = holiday.type;
                holidays.add(dto);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        model.addAttribute("countries", getSupportedCountries());
        model.addAttribute("requestData", requestData);
        model.addAttribute("holidays", holidays);
        return "home/publicHoliday";
    }

    private List<CalenderificCountry> getSupportedCountries() {
        if(countries.isEmpty()) {
            String uri = "https://calendarific.com/api/v2/countries?api_key="+API_KEY;
            Calenderific ctr;
            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ctr = new Gson().fromJson(response.body(), Calenderific.class);
                countries = ctr.response.countries;

            }catch (Exception ex) {

            }

        }
        return countries;
    }
}
