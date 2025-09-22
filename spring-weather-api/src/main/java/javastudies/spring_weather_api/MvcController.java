package javastudies.spring_weather_api;


import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController{
    private final WeatherService weatherService;
    private boolean fahrenheitFlag = false;
    private String errorMessage = "";


    public MvcController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        try {
           model.addAttribute("userName", System.getProperty("user.name"));
           WeatherData weatherData = weatherService.getCurrentWeather(fahrenheitFlag);
           populateWeatherModel(model, weatherData);
           return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/")
    public String toggleFahrenheit(Model model) {
        this.fahrenheitFlag = !this.fahrenheitFlag;
        try {
           model.addAttribute("userName", System.getProperty("user.name"));
           WeatherData weatherData = weatherService.getCurrentWeather(fahrenheitFlag);
           populateWeatherModel(model, weatherData);
           return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    private void populateWeatherModel(Model model, WeatherData weatherData){
        model.addAttribute("userName", System.getProperty("user.name"));
        model.addAttribute("fahnreitFlag", fahrenheitFlag);

        model.addAttribute("address", weatherData.getAddress());
        model.addAttribute("conditions", weatherData.getConditions());
        model.addAttribute("datetime", (LocalDate.parse(weatherData.getDatetime()).getDayOfWeek()).toString().toLowerCase());
        model.addAttribute("description", weatherData.getDescription()); 

        if(fahrenheitFlag){
            model.addAttribute("temperature", Math.round(weatherData.getTemperature()));
            model.addAttribute("temperature_max",  Math.round(weatherData.getTemperatureMax()));
            model.addAttribute("temperature_min",  Math.round(weatherData.getTemperatureMin()));
        }

        else {      
            model.addAttribute("temperature", String.format("%.01f", weatherData.getTemperature()));
            model.addAttribute("temperature_max", String.format("%.01f", weatherData.getTemperatureMax()));
            model.addAttribute("temperature_min", String.format("%.01f", weatherData.getTemperatureMin()));
        }
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public String errorPage(Model model) {

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
