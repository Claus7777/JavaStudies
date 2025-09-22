package javastudies.spring_weather_api;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiFetcher {

    private final RestTemplate restTemplate;

    public WeatherApiFetcher(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> fetchDatafromApi(String url){
        Map<String, Object> result = restTemplate.getForObject(url, Map.class);
        System.out.println(result);
        return result;
    }
}
