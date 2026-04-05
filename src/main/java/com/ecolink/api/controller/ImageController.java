package com.ecolink.api.controller;

import com.ecolink.api.model.Image;
import com.ecolink.api.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PatchMapping("/images")
    public ResponseEntity<?> updateImage(){}

    @DeleteMapping("/images")
    public ResponseEntity<?> deleteImage(){}


}
