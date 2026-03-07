package com.ecolink.api.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "books")
public class Book {

    @Id
    @Setter
    private String id;

    @Size(max = 200)
    @NotBlank
    @Field
    private String title;

    @NotBlank
    @Field
    private String author;

    @Field
    private int publishedYear;

    @Indexed(unique = true)
    @Field
    private String isbn;
}
