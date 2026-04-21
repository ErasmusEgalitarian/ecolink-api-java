package com.ecolink.api.controller;

import com.ecolink.api.dto.EcopointListItemDTO;
import com.ecolink.api.service.EcoPointService;
import com.ecolink.api.service.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api")
public class EcopointController {


	//Using constructor injection
	private final EcoPointService ecoPointService;
	private final ImageService imageService;

	public EcopointController(EcoPointService ecoPointService, ImageService imageService) {
		this.ecoPointService = ecoPointService;
		this.imageService = imageService;
	}
	@Operation(summary="Test API", description="simple endpoint to verify that the API is running")
	@ApiResponse(value={
			@ApiResponse(responseCode = "200", description = "API is running")
	})
	@GetMapping("/test")
	public String test() {
		return "API is running";
	}
	@Operation(summary="Get Ecopoint", description="Returns a list of ecopoints, filteret by latitude and longitude")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Eco points retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
	})
	@GetMapping("/ecopoints")
	public ResponseEntity<?> getEcoPoints(
			@RequestParam(required = false) Double lat,
			@RequestParam(required = false) Double lng,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit) {

		if (page < 1 || limit < 1 || limit > 50) {
			return ResponseEntity.badRequest().body(Map.of(
					"success", false,
					"message", "Invalid pagination parameters"
			));
		}

		Page<EcopointListItemDTO> result = ecoPointService.getEcoPoints(lat, lng, page, limit);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"data", result.getContent(),
				"pagination", Map.of(
						"page", page,
						"limit", limit,
						"total", result.getTotalElements(),
						"totalPages", result.getTotalPages()
				)
		));
	}
}
