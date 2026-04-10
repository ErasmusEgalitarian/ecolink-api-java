package com.ecolink.api.service;

import com.ecolink.api.model.ImageResolutions;
import com.ecolink.api.repository.ImageRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ecolink.api.model.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;

@Service
public class ImageProcessingService {

    private final ImageRepository imageRepository;
    private final GridFSBucket thumbnailsGridFsBucket;
    private final GridFSBucket mediumGridFsBucket;
    private final GridFSBucket largeGridFsBucket;

    public ImageProcessingService(ImageRepository imageRepository,
                                  GridFSBucket thumbnailsGridFsBucket,
                                  GridFSBucket mediumGridFsBucket,
                                  GridFSBucket largeGridFsBucket) {
        this.imageRepository = imageRepository;
        this.thumbnailsGridFsBucket = thumbnailsGridFsBucket;
        this.mediumGridFsBucket = mediumGridFsBucket;
        this.largeGridFsBucket = largeGridFsBucket;
    }

    @Async
    public void processImageResolutionsAsync(Image image,
                                             BufferedImage bufferedImage,
                                             String originalFilename,
                                             String formatName,
                                             String resizedMimeType) {
        try {
            BufferedImage thumbnailImage = org.imgscalr.Scalr.resize(
                    bufferedImage,
                    org.imgscalr.Scalr.Method.QUALITY,
                    org.imgscalr.Scalr.Mode.FIT_TO_WIDTH,
                    200
            );

            BufferedImage mediumImage = org.imgscalr.Scalr.resize(
                    bufferedImage,
                    org.imgscalr.Scalr.Method.QUALITY,
                    org.imgscalr.Scalr.Mode.FIT_TO_WIDTH,
                    600
            );

            BufferedImage largeImage = org.imgscalr.Scalr.resize(
                    bufferedImage,
                    org.imgscalr.Scalr.Method.QUALITY,
                    org.imgscalr.Scalr.Mode.FIT_TO_WIDTH,
                    1200
            );

            String thumbnailFileId = storeResizedImage(
                    thumbnailImage,
                    "thumb_" + originalFilename,
                    formatName,
                    resizedMimeType,
                    thumbnailsGridFsBucket
            );

            String mediumFileId = storeResizedImage(
                    mediumImage,
                    "medium_" + originalFilename,
                    formatName,
                    resizedMimeType,
                    mediumGridFsBucket
            );

            String largeFileId = storeResizedImage(
                    largeImage,
                    "large_" + originalFilename,
                    formatName,
                    resizedMimeType,
                    largeGridFsBucket
            );

            image.setResolutions(ImageResolutions.builder()
                    .thumbnail(thumbnailFileId)
                    .medium(mediumFileId)
                    .large(largeFileId)
                    .build());

            image.setUpdatedAt(Instant.now());
            imageRepository.save(image);

        } catch (IOException e) {
            throw new RuntimeException("Failed to process image resolutions", e);
        }
    }

    private String storeResizedImage(BufferedImage image,
                                     String originalFilename,
                                     String formatName,
                                     String mimeType,
                                     GridFSBucket bucket) throws IOException {
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, formatName, outputStream);

        org.bson.Document metadata = new org.bson.Document("contentType", mimeType);
        com.mongodb.client.gridfs.model.GridFSUploadOptions options =
                new com.mongodb.client.gridfs.model.GridFSUploadOptions().metadata(metadata);

        try (java.io.ByteArrayInputStream inputStream =
                     new java.io.ByteArrayInputStream(outputStream.toByteArray())) {
            org.bson.types.ObjectId fileId = bucket.uploadFromStream(originalFilename, inputStream, options);
            return fileId.toHexString();
        }
    }
}