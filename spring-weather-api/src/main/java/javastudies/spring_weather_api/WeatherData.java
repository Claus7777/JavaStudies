package javastudies.spring_weather_api;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("weatherData")
public class WeatherData implements Serializable{

    @Id
    private String address;

    private String description;
    private double temperature;
    private double temperatureMax;
    private double temperatureMin;

    private String datetime;
    private String conditions;

    public String getAddress(){ return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public void setTemperature(double temperature, boolean fahnreitFlag) {
        if (fahnreitFlag){
            this.temperature = Math.round(temperature);
        } else {
            this.temperature = (temperature - 32) * 5.0/9.0;
        }
    }

    public void setTemperatureMax(double temperatureMax, boolean fahnreitFlag) {
        if (fahnreitFlag){
            this.temperatureMax = Math.round(temperatureMax);
        } else {
            this.temperatureMax = (temperatureMax - 32) * 5.0/9.0;
        }
    }

    public void setTemperatureMin(double temperatureMin, boolean fahnreitFlag) {
        if (fahnreitFlag){
            this.temperatureMin = Math.round(temperatureMin);
        } else {
            this.temperatureMin = (temperatureMin - 32) * 5.0/9.0;
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public String getDatetime() {return datetime;}
    public void setDatetime(String datetime) {this.datetime = datetime;}
    
    public String getConditions() {return conditions;}
    public void setConditions(String conditions) {this.conditions = conditions;}
}
