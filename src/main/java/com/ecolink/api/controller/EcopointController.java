package com.ecolink.api.controller;

import com.ecolink.api.dto.EcoPointListItemDTO;
import com.ecolink.api.model.Image;
import com.ecolink.api.service.EcoPointService;
import com.ecolink.api.service.ImageService;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.Part;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	//Image upload endpoints
	@PostMapping("/images")
	public ResponseEntity<?> handleImageUpload(@RequestPart("meta-data") MultipartFile metadata, @RequestPart("file-data") MultipartFile filedata){

		return ResponseEntity.accepted();
	}


	/*	GET /api/images returns paginated list of all images (default 20 per page)
		GET /api/images supports filters: isPublished=true, createdBy=userId  */
	@GetMapping("/images")
	public ResponseEntity<?> getImageList(
			@RequestParam(required = false) Boolean isPublished,
			@RequestParam(required = false) String createdBy,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int limit) {

		Page<Image> images = imageService.getImagesInList(isPublished, createdBy, page, limit);

		return ResponseEntity.ok(Map.of(
				"success", true,
				"data", images.getContent(),
				"pagination", Map.of(
						"page", page,
						"limit", limit,
						"total", images.getTotalElements(),
						"totalPages", images.getTotalPages()
				)
		));
	}

	@GetMapping("/images/{id}")
	public Image getImageById(@PathVariable String id){
			return imageService.findById(id);
	}

	@PatchMapping("/images")
	public ResponseEntity<?> updateImage(){}

	@DeleteMapping("/images")
	public ResponseEntity<?> deleteImage(){}


}
