package com.application.server.model;

import java.io.Serializable;

public class UserRide implements Serializable {

	private int userRideId;
	private int rideId;
	private int userId;
	private int travellerId;
	private double rating;
	private long created;
	
	// pickup point
	private String fromAddress;
	private double startLat;
	private double startLon;
	private String startGooglePlaceId;

	// drop point
	private String toAddress;
	private double dropLat;
	private double dropLon;
	private String dropGooglePlaceId;
	
	private int status;
	private String description;

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

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getStartLon() {
		return startLon;
	}

	public void setStartLon(double startLon) {
		this.startLon = startLon;
	}

	public String getStartGooglePlaceId() {
		return startGooglePlaceId;
	}

	public void setStartGooglePlaceId(String startGooglePlaceId) {
		this.startGooglePlaceId = startGooglePlaceId;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public double getDropLat() {
		return dropLat;
	}

	public void setDropLat(double dropLat) {
		this.dropLat = dropLat;
	}

	public double getDropLon() {
		return dropLon;
	}

	public void setDropLon(double dropLon) {
		this.dropLon = dropLon;
	}

	public String getDropGooglePlaceId() {
		return dropGooglePlaceId;
	}

	public void setDropGooglePlaceId(String dropGooglePlaceId) {
		this.dropGooglePlaceId = dropGooglePlaceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
