package javastudies.spring_weather_api;


import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController{
    private final WeatherService weatherService;
    private boolean fahrenheitFlag = false;


    public MvcController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("userName", System.getProperty("user.name"));

        WeatherData weatherData = weatherService.getCurrentWeather(fahrenheitFlag);
        model.addAttribute("fahnreitFlag", fahrenheitFlag);

        model.addAttribute("address", weatherData.getAddress());
        model.addAttribute("conditions", weatherData.getConditions());
        model.addAttribute("datetime", (LocalDate.parse(weatherData.getDatetime()).getDayOfWeek()).toString().toLowerCase());
        model.addAttribute("description", weatherData.getDescription()); 
        model.addAttribute("temperature", String.format("%.01f", weatherData.getTemperature()));
        model.addAttribute("temperature_max", String.format("%.01f", weatherData.getTemperature_max()));
        model.addAttribute("temperature_min", String.format("%.01f", weatherData.getTemperature_min()));

        return "home";
    }

    @PostMapping("/")
    public String toggleFahrenheit(Model model) {
        this.fahrenheitFlag = !this.fahrenheitFlag;
        
        // Re-fetch all data and return to the same page
        model.addAttribute("userName", System.getProperty("user.name"));
        model.addAttribute("fahrenheitFlag", fahrenheitFlag);

        WeatherData weatherData = weatherService.getCurrentWeather(fahrenheitFlag);
        
        model.addAttribute("address", weatherData.getAddress());
        model.addAttribute("conditions", weatherData.getConditions());
        model.addAttribute("datetime", (LocalDate.parse(weatherData.getDatetime()).getDayOfWeek()).toString().toLowerCase());
        model.addAttribute("description", weatherData.getDescription()); 
        model.addAttribute("temperature", String.format("%.01f", weatherData.getTemperature()));
        model.addAttribute("temperature_max", String.format("%.01f", weatherData.getTemperature_max()));
        model.addAttribute("temperature_min", String.format("%.01f", weatherData.getTemperature_min()));

        return "home";
    }
}
