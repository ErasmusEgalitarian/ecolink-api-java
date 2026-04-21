package com.ecolink.api.dto;

import java.time.Instant;

import com.ecolink.api.model.VideoMetadata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Response DTO representing a video resource")
public class VideoResponseDTO {
	
	@Schema(description = "Unique identifier of the video", example = "vid-123")
	private String id;

	@Schema(description = "URL to watch the video", example = "https://example.com/watch/vid-123")
	private String watchUrl;

	@Schema(description = "Title of the video", example = "How to recycle plastic")
	private String title;

	@Schema(description = "Detailed description of the video", example = "This video explains how to properly recycle plastic materials.")
	private String description;

	@Schema(description = "Thumbnail image URL", example = "https://example.com/thumbnail.jpg")
	private String thumbnail;

	@Schema(description = "Duration of the video in seconds", example = "120.5")
	private Double duration;

	@Schema(description = "Captions or subtitles file URL", example = "https://example.com/captions.vtt")
	private String captions;

	@Schema(description = "Timestamp when the video was uploaded", example = "2024-01-01T12:00:00Z")
	private Instant uploadedAt;

	@Schema(description = "Timestamp when the video was last updated", example = "2024-01-02T15:30:00Z")
	private Instant updatedAt;

	@Schema(description = "ID of the user who created the video", example = "user-456")
	private String createdBy;

	@Schema(description = "Organization ID associated with the video", example = "org-789")
	private String organizationId;

	@Schema(description = "Indicates whether the video is published", example = "true")
	private boolean isPublished;

	@Schema(description = "Additional metadata related to the video")
	private VideoMetadata metadata;
}