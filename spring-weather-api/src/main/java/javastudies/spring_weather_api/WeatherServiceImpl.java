package javastudies.spring_weather_api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiFetcher weatherApiFetcher;

    @Value("${api-url}")
    private String apiUrl;

    public WeatherServiceImpl(WeatherApiFetcher weatherApiFetcher) {
        this.weatherApiFetcher = weatherApiFetcher;;
    }

    @Override
    public WeatherData getCurrentWeather() {
        Map<String, Object> weatherMap = weatherApiFetcher.fetchDatafromApi(apiUrl);
        var daysList = (java.util.ArrayList) weatherMap.get("days");
        Map<String, Object> todayMap = (Map<String, Object>) daysList.get(0);

        WeatherData weatherData = new WeatherData();
        weatherData.setAddress((String) weatherMap.get("address"));
        weatherData.setConditions((String) todayMap.get("conditions"));
        weatherData.setDatetime(((String) todayMap.get("datetime")));
        weatherData.setDescription((String) todayMap.get("description"));

        double temperatureInCelsius = fahrenheitToCelsius((double) todayMap.get("temp"));
        double maxTemperatureInCelsius = fahrenheitToCelsius((double) todayMap.get("tempmax"));
        double minTemperatureInCelsius =  fahrenheitToCelsius((double) todayMap.get("tempmin"));
        
        weatherData.setTemperature(temperatureInCelsius);
        weatherData.setTemperature_max(maxTemperatureInCelsius);
        weatherData.setTemperature_min(minTemperatureInCelsius);

        return weatherData;
    }

    private double fahrenheitToCelsius(double tempInFahrenheit) {
        return (tempInFahrenheit - 32) * 5.0/9.0;
    }
}
