package com.ecolink.api.controller;

import com.ecolink.api.dto.EcoPointListItemDTO;
import com.ecolink.api.service.EcoPointService;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.Part;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EcopointController {

	private final EcoPointService ecoPointService;

	public EcopointController(EcoPointService ecoPointService) {
		this.ecoPointService = ecoPointService;
	}

	@GetMapping("/test")
	public String test() {
		return "API is running";
	}

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

		Page<EcoPointListItemDTO> result = ecoPointService.getEcoPoints(lat, lng, page, limit);

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

	@PostMapping("/images")
	public ResponseEntity<?> handleImageUpload(@RequestPart("meta-data") Part metadata, @RequestPart("file-data") FilePart filedata){

		return ResponseEntity.accepted();
	}
}
