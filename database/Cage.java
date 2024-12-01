package com.example.demo.database;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // null값 제외
public class Cage {
    private String cage_serial_number;
    private String username;
    private String cage_name;
    private String animal_type;
    private String env_temperature;
    private String env_humidity;
    private String env_lighting;
    private String env_water_level;
    
    private String setType;
	private String setValue;
    
    //getter and setter
    public String getCage_serial_number() {
		return cage_serial_number;
	}
	public void setCage_serial_number(String cage_serial_number) {
		this.cage_serial_number = cage_serial_number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCage_name() {
		return cage_name;
	}
	public void setCage_name(String cage_name) {
		this.cage_name = cage_name;
	}
	public String getAnimal_type() {
		return animal_type;
	}
	public void setAnimal_type(String animal_type) {
		this.animal_type = animal_type;
	}
	public String getEnv_temperature() {
		return env_temperature;
	}
	public void setEnv_temperature(String env_temperature) {
		this.env_temperature = env_temperature;
	}
	public String getEnv_humidity() {
		return env_humidity;
	}
	public void setEnv_humidity(String env_humidity) {
		this.env_humidity = env_humidity;
	}
	public String getEnv_lighting() {
		return env_lighting;
	}
	public void setEnv_lighting(String env_lighting) {
		this.env_lighting = env_lighting;
	}
	public String getEnv_water_level() {
		return env_water_level;
	}
	public void setEnv_water_level(String env_water_level) {
		this.env_water_level = env_water_level;
	}
	
	// Cage 설정값 업데이트
	public String getSetType() {
		return setType;
	}
	public void setSetType(String setType) {
		this.setType = setType;
	}
	public String getSetValue() {
		return setValue;
	}
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
}

