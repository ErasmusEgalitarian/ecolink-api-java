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
    public Page<Image> getImagesInList(boolean isPublished, String createdBy){
        //setting local variables
        int page = 1;
        int limit = 20;

        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("userId").ascending());

    }

}
