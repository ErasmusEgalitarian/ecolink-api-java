package com.ecolink.api.dto;

import com.ecolink.api.model.enums.ConditionEcopoint;
import com.ecolink.api.model.enums.StatusEcopoint;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO for creating or updating an Ecopoint")
public class RequestEcopoint {

	@Schema(description = "Name of the ecopoint", example = "UNB Ecopoint 1")
	private String Name;

	@Schema(description = "Latitude coordinate", example = "55.6761")
	private double Latitude;

	@Schema(description = "Longitude coordinate", example = "12.5683")
	private double Longitude;

	@Schema(description = "Current status of the ecopoint", example = "ACTIVE")
	private StatusEcopoint Status;

	@Schema(description = "Condition of the ecopoint", example = "GOOD")
	private ConditionEcopoint Condition;
	
	public RequestEcopoint(String name, double latitude, double longitude, StatusEcopoint status, ConditionEcopoint condition) {
		this.Name = name;
		this.Latitude = latitude;
		this.Longitude = longitude;
		this.Status = status;
		this.Condition = condition;
	}
	
	public String getName() {
		return Name;
	}
	public double getLatitude() {
		return Latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public StatusEcopoint  getStatus() {
		return Status;
	}
	public void setStatus(StatusEcopoint status) {
		Status = status;
	}
	public ConditionEcopoint getCondition() {
		return Condition;
	}
	public void setCondition(ConditionEcopoint condition) {
		Condition = condition;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public void setLatitude(double latitude) {
		this.Latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.Longitude = longitude;
	}
}
