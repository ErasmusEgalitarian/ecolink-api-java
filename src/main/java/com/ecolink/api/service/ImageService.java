package com.ecolink.api.service;

import com.ecolink.api.model.Image;
import com.ecolink.api.repository.ImageRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ImageService {

    //Field Injection for imageRepository, and GridFSbucket
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private GridFSBucket imagesGridFsBucket;

    //used by /images/{id}
    // Optional<Image> is used because findById may or may not return a result.
    // It lets us handle the "not found" case explicitly without working directly with null.
    public Image findById(String id) {
        Optional<Image> e = imageRepository.findById(id);
            if (e.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image with id %s not found", id));
            };
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
}
