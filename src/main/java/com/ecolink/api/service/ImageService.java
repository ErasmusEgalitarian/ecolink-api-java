package com.ecolink.api.service;

import com.mongodb.client.gridfs.GridFSBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private GridFSBucket imagesGridFsBucket;

    String uploadImage(MultipartFile file, String altText, String title);

    GridFsResource downloadImage(String fileId);

    void deleteImage(String fileId);



}
