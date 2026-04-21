package com.ecolink.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Schema(description = "Standard error response returned by the API")
public class ErrorResponse {

    @Schema(description = "Indicates whether the request was successful", example = "false")
    private boolean success;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error type", example = "Bad Request")
    private String error;

    @Schema(description = "Detailed error message", example = "Missing parameter: page")
    private String message;

    @Schema(description = "Timestamp when the error occurred", example = "2024-01-01T12:00:00Z")
    private Instant timestamp;
}