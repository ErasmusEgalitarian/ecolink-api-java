package com.ecolink.api.dto;

import com.ecolink.api.model.enums.ConditionEcopoint;
import com.ecolink.api.model.enums.StatusEcopoint;

public class RequestEcopoint {

	private String Name;
	private double Latitude;
	private double Longitude;
	private StatusEcopoint  Status;
	private ConditionEcopoint Condition;
	
	public RequestEcopoint(String name, double latitude, double longitude, StatusEcopoint status, ConditionEcopoint condition) {
		
		Name = name;
		Latitude = latitude;
		Longitude = longitude;
		Status = status;
		Condition = condition;
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
}
