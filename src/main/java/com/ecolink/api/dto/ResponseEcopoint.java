package com.ecolink.api.dto;

import java.time.LocalDate;
import com.ecolink.api.model.enums.ConditionEcopoint;
import com.ecolink.api.model.enums.StatusEcopoint;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing an Ecopoint")
public class ResponseEcopoint {
	
	@Schema(description = "Unique identifier of the ecopoint", example = "eco-123")
	private final String id;

	@Schema(description = "Name of the ecopoint", example = "UNB Ecopoint 1")
	private final String name;

	@Schema(description = "Latitude coordinate", example = "55.6761")
	private final double latitude;

	@Schema(description = "Longitude coordinate", example = "12.5683")
	private final double longitude;

	@Schema(description = "Date of last status update", example = "2024-01-01")
	private LocalDate TimeForStatusUpdate;

	@Schema(description = "Date of last condition update", example = "2024-01-02")
	private LocalDate TimeForConditionUpdate;

	@Schema(description = "Current status of the ecopoint", example = "ACTIVE")
	private StatusEcopoint Status;

	@Schema(description = "Current condition of the ecopoint", example = "GOOD")
	private ConditionEcopoint Condition;
	
	public ResponseEcopoint(String id, String name, double latitude, double longitude, LocalDate timeForStatusUpdate,
		   LocalDate timeForConditionUpdate, StatusEcopoint status, ConditionEcopoint condition) {
		
		this.id = id;
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