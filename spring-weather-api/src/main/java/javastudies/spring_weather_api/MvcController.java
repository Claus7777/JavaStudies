package javastudies.spring_weather_api;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController{
    private final WeatherService weatherService;


    public MvcController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("userName", System.getProperty("user.name"));

        WeatherData weatherData = weatherService.getCurrentWeather();

        model.addAttribute("address", weatherData.getAddress());
        model.addAttribute("conditions", weatherData.getConditions());
        model.addAttribute("datetime", (LocalDate.parse(weatherData.getDatetime()).getDayOfWeek()).toString().toLowerCase());
        model.addAttribute("description", weatherData.getDescription()); 
        model.addAttribute("temperature", String.format("%.02f", weatherData.getTemperature()));
        model.addAttribute("temperature_max", String.format("%.02f", weatherData.getTemperature_max()));
        model.addAttribute("temperature_min", String.format("%.02f", weatherData.getTemperature_min()));

        return "home";
    }
}
