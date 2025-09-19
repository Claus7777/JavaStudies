package javastudies.spring_weather_api;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("weatherData")
public class WeatherData implements Serializable{

    @Id
    private String address;

    private String description;
    private double temperatureC;      // Celsius
    private double temperatureF;      // Fahrenheit
    private double temperatureMaxC;
    private double temperatureMaxF;
    private double temperatureMinC;
    private double temperatureMinF;

    private String datetime;
    private String conditions;

    public String getAddress(){ return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public void setTemperature(double temperature) {
        this.temperatureF = temperature;
        this.temperatureC = (temperature - 32) * 5.0/9.0;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMaxF = temperatureMax;
        this.temperatureMaxC = (temperatureMax - 32) * 5.0/9.0;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMinF = temperatureMin;
        this.temperatureMinC = (temperatureMin - 32) * 5.0/9.0;
    }

    public double getTemperature(boolean fahrenheit) {
        return fahrenheit ? temperatureF : temperatureC;
    }

    public double getTemperatureMax(boolean fahrenheit) {
        return fahrenheit ? temperatureMaxF : temperatureMaxC;
    }

    public double getTemperatureMin(boolean fahrenheit) {
        return fahrenheit ? temperatureMinF : temperatureMinC;
    }

    public String getDatetime() {return datetime;}
    public void setDatetime(String datetime) {this.datetime = datetime;}
    
    public String getConditions() {return conditions;}
    public void setConditions(String conditions) {this.conditions = conditions;}
}
