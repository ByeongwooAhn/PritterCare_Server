package com.example.demo.login;

import java.util.List;

import com.example.demo.database.Cage;
import com.example.demo.database.Reservation;

public class ReturnAppResponse {
	private List<Cage> cages;
	private List<Reservation> reservations;
	
	public List<Cage> getCages() {
		return cages;
	}
	public void setCages(List<Cage> cages) {
		this.cages = cages;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

}
