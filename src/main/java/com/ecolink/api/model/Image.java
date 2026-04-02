package com.ecolink.api.model;

import lombok.*;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "images")
public class Image {

    @Id
    @Setter
    private String id;

    private ObjectId _id;

    private String alt_text;

    private String title;

    private String description;

    private String caption;

    @CreatedDate
    private Instant uploadedAt;

    @LastModifiedDate
    private Instant updatedAt;

    @CreatedBy
    private User createdBy;

    @Builder.Default
    private Boolean isPublished = false;

    private ObjectId imageUrl;

    resolutions: {
        thumbnail (GridFS fileId),
                medium (GridFS fileId),
                large (GridFS fileId)
    }


    metadata: {
        fileSize,
        format,
        dimensions: { width, height };
    }
}
