package com.example.demo.model;

import java.util.List;
import java.util.UUID; 

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Ecopoint {

	private String EcopointID = UUID.randomUUID().toString();
	private final String EcopointName;
	private String EcopointAddress;
	private final GPSLocations EcopointLocation;
	private StatusEcopoint EcopointStatus;
	private ConditionEcopoint EcopointCondition;
	private String operatingHours;
	private OperatingEcopoint EcopointOperating;
	private List<AcceptedMaterial> acceptedMaterials;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	public Ecopoint(String ecopointID, String ecopointName, String ecopointAddress,
			GPSLocations ecopointLocation, StatusEcopoint ecopointStatus, ConditionEcopoint ecopointCondition,
			String operatingHours, OperatingEcopoint ecopointOperating, GeoJsonPoint location) {
		super();
		EcopointID = ecopointID;
		EcopointName = ecopointName;
		EcopointAddress = ecopointAddress;
		EcopointLocation = ecopointLocation;
		EcopointStatus = ecopointStatus;
		EcopointCondition = ecopointCondition;
		this.operatingHours = operatingHours;
		EcopointOperating = ecopointOperating;
		this.location = location;
	}
	
	
	public String getEcopointID() {
		return EcopointID;
	}
	public String getEcopointName() {
		return EcopointName;
	}
	public GPSLocations getEcopointLocation() {
		return EcopointLocation;
	}
	public StatusEcopoint getEcopointStatus() {
		return EcopointStatus;
	}	
	public ConditionEcopoint getEcopointCondition() {
		return EcopointCondition;
	}
	public void setEcopointStatus(StatusEcopoint ecopointStatus) {
		EcopointStatus = ecopointStatus;
	}
	public void setEcopointCondition(ConditionEcopoint ecopointCondition) {
		EcopointCondition = ecopointCondition;
	}
	public String getEcopointAddress() {
		return EcopointAddress;
	}
	public void setEcopointAddress(String ecopointAddress) {
		EcopointAddress = ecopointAddress;
	}
	public OperatingEcopoint getEcopointOperating(){
		return EcopointOperating;
	}
	public void setEcopointOperating(OperatingEcopoint ecopointOperating){
		EcopointOperating = ecopointOperating;	
	}
	public String getOperatingHours() {
		return operatingHours;
	}
	public void setOperatingHours(String operatingHours) {
		this.operatingHours = operatingHours;
	}
	public GeoJsonPoint getLocation() {
		return location;
	}
	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}


	public List<AcceptedMaterial> getAcceptedMaterials() {
		return acceptedMaterials;
	}


	public void setAcceptedMaterials(List<AcceptedMaterial> acceptedMaterials) {
		this.acceptedMaterials = acceptedMaterials;
	}
}