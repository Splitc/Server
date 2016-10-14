package com.application.server.model;

import java.io.Serializable;

public class UserRide implements Serializable {

	private int userRideId;
	private int rideId;
	private int userId;
	private int travellerId;
	private double rating;
	private long created;

	public UserRide() {
	}

	public int getUserRideId() {
		return userRideId;
	}

	public void setUserRideId(int userRideId) {
		this.userRideId = userRideId;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTravellerId() {
		return travellerId;
	}

	public void setTravellerId(int travellerId) {
		this.travellerId = travellerId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}
	
}
