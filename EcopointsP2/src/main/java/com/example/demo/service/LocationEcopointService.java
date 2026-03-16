package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Ecopoint;

public interface LocationEcopointService {
	List<Ecopoint> findNearby(double longitude, double latitude, double maxDistance);
	
	
}
