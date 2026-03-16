package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Ecopoint;
import com.example.demo.repository.RepositoryEcopointsFromDB;

@Service

public class LocationServiceEcopointImplement implements LocationEcopointService {

	private RepositoryEcopointsFromDB repositoryEcopointsFromDB;
	
	public List<Ecopoint> findNearby(double longitude, double latitude, double maxDistance) {
		
		if(latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException("Needs to be from -90 to 90");
		}
		if(longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException("Needs to be from -180 to 180");
		}
		if (maxDistance > 50) {
			throw new IllegalArgumentException("Cant be more than 50 km  - Maximum is 50 km");
		}
		return repositoryEcopointsFromDB.findNearby(longitude, latitude, maxDistance);
	}

	public LocationServiceEcopointImplement(RepositoryEcopointsFromDB repositoryEcopointsFromDB) {
		super();
		this.repositoryEcopointsFromDB = repositoryEcopointsFromDB;
	}
}
