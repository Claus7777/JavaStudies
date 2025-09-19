package javastudies.spring_weather_api;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("weatherData")
    public WeatherData getCurrentWeather(boolean fahnreitFlag) {
        Map<String, Object> weatherMap = weatherApiFetcher.fetchDatafromApi(apiUrl);
        ArrayList daysList = (ArrayList) weatherMap.get("days");
        Map<String, Object> todayMap = (Map<String, Object>) daysList.get(0);

        WeatherData weatherData = new WeatherData();
        weatherData.setAddress((String) weatherMap.get("address"));
        weatherData.setConditions(((String) todayMap.get("conditions")).toLowerCase());
        weatherData.setDatetime(((String) todayMap.get("datetime")));
        String descriptionWithNoPeriod = ((String) todayMap.get("description")).replace(".", "");
        descriptionWithNoPeriod = descriptionWithNoPeriod.toLowerCase();
        weatherData.setDescription(descriptionWithNoPeriod);

        if(!fahnreitFlag){
            double temperatureInCelsius = fahrenheitToCelsius((double) todayMap.get("temp"));
            double maxTemperatureInCelsius = fahrenheitToCelsius((double) todayMap.get("tempmax"));
            double minTemperatureInCelsius =  fahrenheitToCelsius((double) todayMap.get("tempmin"));
                    
            weatherData.setTemperature(temperatureInCelsius);
            weatherData.setTemperature_max(maxTemperatureInCelsius);
            weatherData.setTemperature_min(minTemperatureInCelsius);
        }
        else { 
            weatherData.setTemperature((double) todayMap.get("temp"));
            weatherData.setTemperature_max((double) todayMap.get("tempmax"));
            weatherData.setTemperature_min((double) todayMap.get("tempmin"));
        }

        return weatherData;
    }


    private double fahrenheitToCelsius(double tempInFahrenheit) {
        return (tempInFahrenheit - 32) * 5.0/9.0;
    }
}
