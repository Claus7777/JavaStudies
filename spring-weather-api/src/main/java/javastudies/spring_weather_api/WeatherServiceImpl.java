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
    @Cacheable(value = "weatherData")
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

        double temp = (double) todayMap.get("temp");
        double maxTemp = (double) todayMap.get("tempmax");
        double minTemp = (double) todayMap.get("tempmin");

        weatherData.setTemperature(temp);
        weatherData.setTemperatureMax(maxTemp);
        weatherData.setTemperatureMin(minTemp);

        System.out.println(temp);

        return weatherData;
    }
}
