package com.ecolink.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.api.dto.APIResponse;
import com.ecolink.api.model.Ecopoint;
import com.ecolink.api.service.LocationEcopointService;

@RestController
@RequestMapping("/api/ecopoints")
public class EcopointController {

	private final LocationEcopointService EcopointList;

	public EcopointController(LocationEcopointService ecopointList) {
		super();
		EcopointList = ecopointList;
	}
	 @GetMapping("/nearby")
	 public APIResponse getNearbyEcopoints(@RequestParam Double lat, @RequestParam Double lng, @RequestParam(required = false, defaultValue = "10") Double radius) {
		 List<Ecopoint> result = EcopointList.findNearby(lng, lat, radius);
		 return new APIResponse(true, result, "Closest Ecopoint was found");
	 }
}
