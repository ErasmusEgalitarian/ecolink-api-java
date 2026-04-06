package com.ecolink.api.controller;

import com.ecolink.api.dto.UpdateImageRequest;
import com.ecolink.api.model.Image;
import com.ecolink.api.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageController {


    //Using constructor injection
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //Image upload endpoints
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadImage(
            @RequestPart("imageFile") MultipartFile imageFile,
            @RequestPart("title") String title,
            @RequestPart("alt_text") String altText,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "caption", required = false) String caption,
            Authentication authentication
    ) throws IOException {

        Image created = imageService.uploadImage(imageFile, title, altText, description, caption, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*	GET /api/images returns paginated list of all images (default 20 per page)
        GET /api/images supports filters: isPublished=true, createdBy=userId  */
    @GetMapping("/images")
    public ResponseEntity<?> getImageList(
            @RequestParam(required = false) Boolean isPublished,
            @RequestParam(required = false) String createdBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {

        //Input validation for page and limit
        if (page < 1 || limit < 1 || limit > 100) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid pagination parameters"));
        }

        //building the response for the page response
        Page<Image> images = imageService.getImagesInList(isPublished, createdBy, page, limit);

        //what we will include in our response.
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", images.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", images.getTotalElements(),
                        "totalPages", images.getTotalPages())));
    }

    @GetMapping("/images/{id}")
    public Image getImageById(@PathVariable String id){
        return imageService.findById(id);
    }

    @PatchMapping("/images/{id}")
    public ResponseEntity<Image> updateImage(
            @PathVariable String id,
            @RequestBody UpdateImageRequest request,
            Authentication authentication
    ){
        Image updatedImage = imageService.updateImageMetadata(id, request, authentication);
        return ResponseEntity.ok(updatedImage);
    }

    // DELETE /api/images/{id}
    // Deletes a specific image (only owner or admin allowed)
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(
            @PathVariable String id,
            Authentication authentication
    ){
        imageService.deleteImage(id, authentication);

        // 204 No Content = standard for successful delete
        return ResponseEntity.noContent().build();
    }
}
