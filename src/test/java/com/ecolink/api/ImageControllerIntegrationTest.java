package com.ecolink.api;

import com.ecolink.api.model.Image;
import com.ecolink.api.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ImageRepository imageRepository;

    @BeforeEach
    void setup() {
        imageRepository.deleteAll();

        imageRepository.save(Image.builder()
                .title("Published image")
                .alt_text("Alt text 1")
                .description("Description 1")
                .caption("Caption 1")
                .createdBy("user1")
                .isPublished(true)
                .uploadedAt(Instant.now())
                .updatedAt(Instant.now())
                .build());

        imageRepository.save(Image.builder()
                .title("Draft image")
                .alt_text("Alt text 2")
                .description("Description 2")
                .caption("Caption 2")
                .createdBy("user2")
                .isPublished(false)
                .uploadedAt(Instant.now())
                .updatedAt(Instant.now())
                .build());
    }

    @Test
    @WithMockUser(username = "editor", roles = {"CONTENT_CREATOR"})
    void shouldReturnImages() throws Exception {
        mockMvc.perform(get("/api/images?page=1&limit=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.pagination.page").value(1))
                .andExpect(jsonPath("$.pagination.limit").value(20));
    }

    @Test
    @WithMockUser(username = "editor", roles = {"CONTENT_CREATOR"})
    void shouldFilterByPublishedStatus() throws Exception {
        mockMvc.perform(get("/api/images?isPublished=true&page=1&limit=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Published image"))
                .andExpect(jsonPath("$.data[0].isPublished").value(true));
    }

    @Test
    @WithMockUser(username = "editor", roles = {"CONTENT_CREATOR"})
    void shouldFilterByCreatedBy() throws Exception {
        mockMvc.perform(get("/api/images?createdBy=user2&page=1&limit=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].createdBy").value("user2"))
                .andExpect(jsonPath("$.data[0].title").value("Draft image"));
    }

    @Test
    @WithMockUser(username = "editor", roles = {"CONTENT_CREATOR"})
    void shouldReturnBadRequestForInvalidPagination() throws Exception {
        mockMvc.perform(get("/api/images?page=0&limit=200"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void shouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/images?page=1&limit=20"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"CONTENT_CREATOR"})
    void shouldUploadImage() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.png",
                "image/png",
                validImageBytes()
        );

        org.springframework.mock.web.MockPart title =
                new org.springframework.mock.web.MockPart("title", "Test title".getBytes());
        title.getHeaders().setContentType(org.springframework.http.MediaType.TEXT_PLAIN);

        org.springframework.mock.web.MockPart altText =
                new org.springframework.mock.web.MockPart("alt_text", "Alt text".getBytes());
        altText.getHeaders().setContentType(org.springframework.http.MediaType.TEXT_PLAIN);

        org.springframework.mock.web.MockPart description =
                new org.springframework.mock.web.MockPart("description", "Description".getBytes());
        description.getHeaders().setContentType(org.springframework.http.MediaType.TEXT_PLAIN);

        org.springframework.mock.web.MockPart caption =
                new org.springframework.mock.web.MockPart("caption", "Caption".getBytes());
        caption.getHeaders().setContentType(org.springframework.http.MediaType.TEXT_PLAIN);

        mockMvc.perform(multipart("/api/images")
                        .file(imageFile)
                        .part(title)
                        .part(altText)
                        .part(description)
                        .part(caption))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.alt_text").value("Alt text"))
                .andExpect(jsonPath("$.createdBy").value("user1"))
                .andExpect(jsonPath("$.imageMetaData.format").value("image/png"));
    }
    @Test
    @WithMockUser(username = "user1", roles = {"CONTENT_CREATOR"})
    void shouldPatchImageMetadataAndUpdateUpdatedAt() throws Exception {
        Image image = new Image();
        image.setTitle("Old title");
        image.setAlt_text("Old alt");
        image.setDescription("Old description");
        image.setCaption("Old caption");
        image.setCreatedBy("user1");
        image.setIsPublished(false);
        image.setUploadedAt(Instant.now().minusSeconds(60));
        image.setUpdatedAt(Instant.now().minusSeconds(60));
        image = imageRepository.save(image);

        Instant oldUpdatedAt = image.getUpdatedAt();

        MockMultipartFile title = new MockMultipartFile(
                "title", "", "text/plain", "New title".getBytes()
        );

        MockMultipartFile altText = new MockMultipartFile(
                "alt_text", "", "text/plain", "New alt".getBytes()
        );

        MockMultipartFile description = new MockMultipartFile(
                "description", "", "text/plain", "New description".getBytes()
        );

        MockMultipartFile caption = new MockMultipartFile(
                "caption", "", "text/plain", "New caption".getBytes()
        );

        mockMvc.perform(multipart("/api/images/{id}", image.getId())
                        .file(title)
                        .file(altText)
                        .file(description)
                        .file(caption)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New title"))
                .andExpect(jsonPath("$.alt_text").value("New alt"))
                .andExpect(jsonPath("$.description").value("New description"))
                .andExpect(jsonPath("$.caption").value("New caption"));

        Image updated = imageRepository.findById(image.getId()).orElseThrow();
        assertEquals("New title", updated.getTitle());
        assertEquals("New alt", updated.getAlt_text());
        assertEquals("New description", updated.getDescription());
        assertEquals("New caption", updated.getCaption());
        assertTrue(updated.getUpdatedAt().isAfter(oldUpdatedAt));

    }

    @Test
    @WithMockUser(username = "user1", roles = {"CONTENT_CREATOR"})
    void shouldDeleteImage() throws Exception {
        Image image = new Image();
        image.setTitle("Delete me");
        image.setAlt_text("Alt text");
        image.setCreatedBy("user1");
        image.setIsPublished(false);
        image.setUploadedAt(Instant.now());
        image.setUpdatedAt(Instant.now());
        image = imageRepository.save(image);

        mockMvc.perform(delete("/api/images/{id}", image.getId()))
                .andExpect(status().isNoContent());

        assertFalse(imageRepository.findById(image.getId()).isPresent());
    }

    private byte[] validImageBytes() throws Exception {
        java.awt.image.BufferedImage image =
                new java.awt.image.BufferedImage(10, 10, java.awt.image.BufferedImage.TYPE_INT_RGB);

        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", outputStream);

        return outputStream.toByteArray();
    }
}