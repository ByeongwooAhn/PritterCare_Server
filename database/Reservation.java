package com.example.demo.database;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL) // null값 제외
public class Reservation {
	private String cage_serial_number;
	private String reserve_name;
	private LocalDate reserve_date;
	private LocalTime reserve_time;
	private int day_loop;
	private int time_loop;
	private String reserve_type;
	
	private String origin_reserve_name;
	private LocalDate origin_reserve_date;
	private LocalTime origin_reserve_time;
	private int origin_day_loop;
	private int origin_time_loop;
	private String origin_reserve_type;
	
	//Getter and Setter
	public String getCage_serial_number() {
		return cage_serial_number;
	}
	public void setCage_serial_number(String cage_serial_number) {
		this.cage_serial_number = cage_serial_number;
	}
	public String getReserve_name() {
		return reserve_name;
	}
	public void setReserve_name(String reserve_name) {
		this.reserve_name = reserve_name;
	}
	public LocalDate getReserve_date() {
		return reserve_date;
	}
	public void setReserve_date(LocalDate reserve_date) {
		this.reserve_date = reserve_date;
	}
	public LocalTime getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(LocalTime reserv_time) {
		this.reserve_time = reserv_time;
	}
	public int getDay_loop() {
		return day_loop;
	}
	public void setDay_loop(int day_loop) {
		this.day_loop = day_loop;
	}
	public int getTime_loop() {
		return time_loop;
	}
	public void setTime_loop(int time_loop) {
		this.time_loop = time_loop;
	}
	public String getReserve_type() {
		return reserve_type;
	}
	public void setReserve_type(String reserve_type) {
		this.reserve_type = reserve_type;
	}
	
	public String getOrigin_reserve_name() {
		return origin_reserve_name;
	}
	public void setOrigin_reserve_name(String origin_reserve_name) {
		this.origin_reserve_name = origin_reserve_name;
	}
	public LocalDate getOrigin_reserve_date() {
		return origin_reserve_date;
	}
	public void setOrigin_reserve_date(LocalDate origin_reserve_date) {
		this.origin_reserve_date = origin_reserve_date;
	}
	public LocalTime getOrigin_reserve_time() {
		return origin_reserve_time;
	}
	public void setOrigin_reserve_time(LocalTime origin_reserve_time) {
		this.origin_reserve_time = origin_reserve_time;
	}
	public int getOrigin_day_loop() {
		return origin_day_loop;
	}
	public void setOrigin_day_loop(int origin_day_loop) {
		this.origin_day_loop = origin_day_loop;
	}
	public int getOrigin_time_loop() {
		return origin_time_loop;
	}
	public void setOrigin_time_loop(int origin_time_loop) {
		this.origin_time_loop = origin_time_loop;
	}
	public String getOrigin_reserve_type() {
		return origin_reserve_type;
	}
	public void setOrigin_reserve_type(String origin_reserve_type) {
		this.origin_reserve_type = origin_reserve_type;
	}
}
