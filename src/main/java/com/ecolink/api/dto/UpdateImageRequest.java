package com.ecolink.api.dto;

import lombok.Data;

@Data
public class UpdateImageRequest {
    private String alt_text;
    private String title;
    private String description;
    private String caption;
    private Boolean isPublished;
}