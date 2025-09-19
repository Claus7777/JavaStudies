package javastudies.spring_weather_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringWeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWeatherApiApplication.class, args);
	}

}
