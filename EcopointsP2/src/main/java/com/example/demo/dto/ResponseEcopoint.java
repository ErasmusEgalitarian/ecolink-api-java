package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.ConditionEcopoint;
import com.example.demo.model.StatusEcopoint;

public class ResponseEcopoint {
	
	private final String id; // vi bruger final så man ikke kan ændre på navnet igen
	private final String name;
	private final double latitude;
	private final double longitude;
	private LocalDate TimeForStatusUpdate;
	private LocalDate TimeForConditionUpdate;
	private StatusEcopoint  Status;
	private ConditionEcopoint Condition;
	
	public ResponseEcopoint(String id, String name, double latitude, double longitude, LocalDate timeForStatusUpdate,
		   LocalDate timeForConditionUpdate, StatusEcopoint status, ConditionEcopoint condition) {
		
		this.id = id; // ændret til this id, fordi Id ikke på være en variable. Ændre fordi id skal stå med småt
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		TimeForStatusUpdate = timeForStatusUpdate;
		TimeForConditionUpdate = timeForConditionUpdate;
		Status = status;
		Condition = condition;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
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