package com.example.demo.controller;

import com.example.demo.model.EcoPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.enums.ConditionEcopoint;
import com.example.demo.model.Ecopoint;
import com.example.demo.model.GPSLocations;
import com.example.demo.model.enums.StatusEcopoint;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class EcopointController {

	@GetMapping("/test")
	public EcoPoint EcopointTest() {
		GPSLocations location = new GPSLocations(-45.0, 33.0);
		EcoPoint ep = new EcoPoint("UNB Ecopoint1", location, StatusEcopoint.NOTFULL, ConditionEcopoint.WORKS);
		return ep;
	}

	@GetMapping("/ecopoints")
	public List<EcoPoint> getAllEcopoint(@RequestParam (required = false) Double lat,
										 @RequestParam (required = false) Double lng,
										 @RequestParam (required = false) Integer page,
										 @RequestParam (required = false) Integer limit) {
		if (lat != null && lng != null){
			//Udregn efter distance
		}
		else {

		}

		return null;
	}
}
