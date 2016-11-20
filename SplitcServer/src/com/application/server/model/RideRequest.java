package com.application.server.model;

public class RideRequest {

	private int rideRequestId;

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

	private int persons;
	private String description;

	private int userId; // wish posting user
	
	public RideRequest(){}

	public int getRideRequestId() {
		return rideRequestId;
	}

	public void setRideRequestId(int rideRequestId) {
		this.rideRequestId = rideRequestId;
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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
