package com.ecolink.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "images")
public class Image {

    @Id
    @Setter
    private String id;

    private _id;
    imageUrl (GridFS fileId),
    alt_text (required),
    title (required),
    description,
    caption,
    resolutions: {
        thumbnail (GridFS fileId),
                medium (GridFS fileId),
                large (GridFS fileId)
    },
    uploadedAt,
    updatedAt,
    createdBy (userId),
    isPublished (boolean, default: false),
    metadata: {
        fileSize,
        format,
        dimensions: { width, height };
        }
}
