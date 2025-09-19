package javastudies.spring_weather_api;

public class WeatherData {
    private String address;
    private String description;
    private double temperature;
    private double temperature_max;
    private double temperature_min;
    private String datetime;
    private String conditions;

    public String getAddress(){ return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getTemperature() {return temperature;}
    public void setTemperature(double temperature) {this.temperature = temperature;}

    public double getTemperature_max() {return temperature_max;}
    public void setTemperature_max(double temperature_max) {this.temperature_max = temperature_max;}

    public double getTemperature_min() {return temperature_min;}
    public void setTemperature_min(double temperature_min) {this.temperature_min = temperature_min;}

    public String getDatetime() {return datetime;}
    public void setDatetime(String datetime) {this.datetime = datetime;}
    
    public String getConditions() {return conditions;}
    public void setConditions(String conditions) {this.conditions = conditions;}
}
