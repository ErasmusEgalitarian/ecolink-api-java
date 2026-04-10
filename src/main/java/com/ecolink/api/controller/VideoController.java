package com.ecolink.api.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecolink.api.dto.VideoResponseDTO;
import com.ecolink.api.service.VideoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

	private final VideoService videoService;
	
	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}
	@Operation(summary = "Upload Video", description = "Uploading of a new video")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadVideo(
	@RequestParam MultipartFile videoFile,
	@RequestParam String title,
	@RequestParam(required=false) String description,
	@RequestParam(required=false) MultipartFile captions) throws IOException {
		VideoResponseDTO dto = videoService.uploadVideo(videoFile, title, description, captions);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	@Operation(summary = "Get all videos", description = "showing a list of all videos by isPublished and createdBy")
	@GetMapping()
	public ResponseEntity<?> getAllVideos(
			@RequestParam(required=false) Boolean isPublished,
			@RequestParam(required=false) String createdBy,
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int limit) {
				Page<VideoResponseDTO> result = videoService.getAllVideos(isPublished, createdBy, page, limit);
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
	@Operation(summary = "Get video by ID", description = "finding a specific video by it's ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> getVideoId(
			@PathVariable String Id) {
		VideoResponseDTO dto = videoService.getVideo(Id);
		return ResponseEntity.ok(dto);
	}
	@Operation(summary = "Update video by id", description = "Updating video title, description, isPublished, and newFile by ID")
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateVideoId(
			@PathVariable String Id,
			@RequestParam(required=false) String title,
			@RequestParam(required=false) String description,
			@RequestParam(required=false) Boolean isPublished,
			@RequestParam(required=false) MultipartFile newFile) throws IOException {
		VideoResponseDTO dto = videoService.updateVideo(Id, title, description, isPublished, newFile);
		return ResponseEntity.ok(dto);
	}
	@Operation(summary = "Delete video by id", description = "Deleting a video by it's ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVideoId(@PathVariable String Id){
		videoService.deleteVideo(Id);
			return ResponseEntity.noContent().build();
	}
}
