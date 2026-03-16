package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.APIResponse;
import com.example.demo.model.Ecopoint;
import com.example.demo.service.LocationEcopointService;

@RestController
@RequestMapping("/api/ecopoints")
public class EcopointController {

	private LocationEcopointService EcopointList;

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
