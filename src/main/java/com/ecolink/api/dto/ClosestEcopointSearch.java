package com.ecolink.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="Funktion that search for the closest ecopoint")
public class ClosestEcopointSearch {
	@Schema(description="Latitude cordinate", example="55.4241")
	private double Latitude;
	@Schema(description="longitude cordinate", example="11.1341")
	private double Longitude;
	@Schema(description="Search radius in kilometers")
	private double RadiusInKM;
	
public ClosestEcopointSearch(double latitude, double longitude, double radiusInKM) {
	
	if(radiusInKM < 10) {
		throw new IllegalArgumentException("Radius can't be less than 10 km or less than 10 km");
	}
	
	Latitude = latitude;
	Longitude = longitude;
	RadiusInKM = radiusInKM;
}

public double getLatitude() {
	return Latitude;
}
public double getLongitude() {
	return Longitude;
}
public double getRadiusInKM() {
	return RadiusInKM;
}
public void setRadiusInKM(double radiusInKM) {
	RadiusInKM = radiusInKM;
}

}