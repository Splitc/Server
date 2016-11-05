package com.application.server.model;

import java.io.Serializable;

public class Ride implements Serializable {

	private int rideId;

	private String fromAddress;
	private double startLat;
	private double startLon;
	private String startGooglePlaceId;

	private String toAddress;
	private double dropLat;
	private double dropLon;
	private String dropGooglePlaceId;

	private int status;
	private long created;
	private long startTime;

	private int requiredPersons;
	private int currentRequiredPersons;
	private String description;

	private int userId; // wish posting user

	public Ride() {
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public int getRequiredPersons() {
		return requiredPersons;
	}

	public void setRequiredPersons(int requiredPersons) {
		this.requiredPersons = requiredPersons;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartGooglePlaceId() {
		return startGooglePlaceId;
	}

	public void setStartGooglePlaceId(String startGooglePlaceId) {
		this.startGooglePlaceId = startGooglePlaceId;
	}

	public String getDropGooglePlaceId() {
		return dropGooglePlaceId;
	}

	public void setDropGooglePlaceId(String dropGooglePlaceId) {
		this.dropGooglePlaceId = dropGooglePlaceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getCurrentRequiredPersons() {
		return currentRequiredPersons;
	}

	public void setCurrentRequiredPersons(int currentRequiredPersons) {
		this.currentRequiredPersons = currentRequiredPersons;
	}
	
}
