package com.ecolink.api.service;

import com.ecolink.api.dto.UpdateImageRequest;
import com.ecolink.api.model.Dimensions;
import com.ecolink.api.model.Image;
import com.ecolink.api.model.ImageMetaData;
import com.ecolink.api.repository.ImageRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final GridFsTemplate gridFsTemplate;

    public ImageService(ImageRepository imageRepository, GridFsTemplate gridFsTemplate) {
        this.imageRepository = imageRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    //used by POST /images
    public Image uploadImage(MultipartFile imageFile,
                             String title,
                             String altText,
                             String description,
                             String caption) throws IOException {

        System.out.println("SERVICE HIT");
        System.out.println("contentType=" + imageFile.getContentType());
        System.out.println("size=" + imageFile.getSize());

        if (imageFile == null || imageFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
        }

        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }

        if (altText == null || altText.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alt_text is required");
        }

        String contentType = imageFile.getContentType();
        String filename = imageFile.getOriginalFilename() != null
                ? imageFile.getOriginalFilename().toLowerCase()
                : "";

        boolean validContentType = List.of("image/jpeg", "image/png", "image/webp", "image/gif")
                .contains(contentType);

        boolean validExtension = filename.endsWith(".jpg")
                || filename.endsWith(".jpeg")
                || filename.endsWith(".png")
                || filename.endsWith(".webp")
                || filename.endsWith(".gif");

        if (!validContentType && !validExtension) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported image format");
        }

        long maxSize = 10L * 1024 * 1024;
        if (imageFile.getSize() > maxSize) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File too large");
        }

        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        if (bufferedImage == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image file");
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        ObjectId fileId;
        try (InputStream inputStream = imageFile.getInputStream()) {
            fileId = gridFsTemplate.store(
                    inputStream,
                    imageFile.getOriginalFilename(),
                    imageFile.getContentType()
            );
        }

        ImageMetaData metaData = ImageMetaData.builder()
                .fileSize(imageFile.getSize())
                .format(imageFile.getContentType())
                .dimensions(new Dimensions(width, height))
                .build();

        Image image = Image.builder()
                .imageUrl(fileId.toHexString())
                .title(title)
                .alt_text(altText)
                .description(description)
                .caption(caption)
                .uploadedAt(Instant.now())
                .updatedAt(Instant.now())
                .isPublished(false)
                .imageMetaData(metaData)
                .build();

        return imageRepository.save(image);
    }


    //used by /images/{id}
    // Optional<Image> is used because findById may or may not return a result.
    // It lets us handle the "not found" case explicitly without working directly with null.
    public Image findById(String id) {
        Optional<Image> e = imageRepository.findById(id);
            if (e.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image with id %s not found", id));
            }
            return e.get();
    }

    //used by /images for getting paginated list
        public Page<Image> getImagesInList(Boolean isPublished, String createdBy,int page, int limit){


            PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("updatedAt").descending());

            if (isPublished != null && createdBy != null) {
                return imageRepository.findByIsPublishedAndCreatedBy(isPublished, createdBy, pageable);
            } else if (isPublished != null) {
                return imageRepository.findByIsPublished(isPublished, pageable);
            } else if (createdBy != null) {
                return imageRepository.findByCreatedBy(createdBy, pageable);
            } else {
                return imageRepository.findAll(pageable);
            }
        }

    // Used by PATCH /api/images/{id}
    // Updates only fields sent in the request
    public Image updateImageMetadata(String id, UpdateImageRequest request, Authentication authentication) {

        Image image = findById(id);

        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = image.getCreatedBy() != null &&
                image.getCreatedBy().equals(currentUsername);

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You are not allowed to update this image");
        }

        if (request.getAlt_text() != null) {
            image.setAlt_text(request.getAlt_text());
        }

        if (request.getTitle() != null) {
            image.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            image.setDescription(request.getDescription());
        }

        if (request.getCaption() != null) {
            image.setCaption(request.getCaption());
        }

        if (request.getIsPublished() != null) {
            image.setIsPublished(request.getIsPublished());
        }

        return imageRepository.save(image);
    }

    // Used by DELETE /api/images/{id}
    // Deletes image if user is owner or admin
    public void deleteImage(String id, Authentication authentication) {

        Image image = findById(id);

        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = image.getCreatedBy() != null &&
                image.getCreatedBy().equals(currentUsername);

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this image");
        }

        imageRepository.deleteById(id);
    }
}
