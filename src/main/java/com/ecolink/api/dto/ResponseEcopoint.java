package com.ecolink.api.dto;

import java.time.LocalDate;
import com.ecolink.api.model.enums.ConditionEcopoint;
import com.ecolink.api.model.enums.StatusEcopoint;

public class ResponseEcopoint {
	
	private String ID; 
	private String Name;
	private double Latitude;
	private double Longitude;
	private LocalDate TimeForStatusUpdate;
	private LocalDate TimeForConditionUpdate;
	private StatusEcopoint  Status;
	private ConditionEcopoint Condition;
	
	public ResponseEcopoint(String iD, String name, double latitude, double longitude, LocalDate timeForStatusUpdate,
		   LocalDate timeForConditionUpdate, StatusEcopoint status, ConditionEcopoint condition) {
		
		ID = iD;
		Name = name;
		Latitude = latitude;
		Longitude = longitude;
		TimeForStatusUpdate = timeForStatusUpdate;
		TimeForConditionUpdate = timeForConditionUpdate;
		Status = status;
		Condition = condition;
	}
	public String getID() {
		return ID;
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
	public LocalDate getTimeForStatusUpdate() {
		return TimeForStatusUpdate;
	}
	public void setTimeForStatusUpdate(LocalDate timeForStatusUpdate) {
		TimeForStatusUpdate = timeForStatusUpdate;
	}
	public LocalDate getTimeForConditionUpdate() {
		return TimeForConditionUpdate;
	}
	public void setTimeForConditionUpdate(LocalDate timeForConditionUpdate) {
		TimeForConditionUpdate = timeForConditionUpdate;
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