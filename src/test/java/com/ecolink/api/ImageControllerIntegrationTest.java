package com.ecolink.api;

import com.ecolink.api.model.Image;
import com.ecolink.api.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        Image image = Image.builder()
                .title("Test image")
                .alt_text("Alt text")
                .description("Description")
                .caption("Caption")
                .createdBy("user1")
                .isPublished(true)
                .uploadedAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        imageRepository.save(image);
    }

    @Test
    @WithMockUser
    void shouldReturnImages() throws Exception {
        mockMvc.perform(get("/api/images?page=1&limit=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pagination.page").value(1))
                .andExpect(jsonPath("$.pagination.limit").value(20));
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestForInvalidPagination() throws Exception {
        mockMvc.perform(get("/api/images?page=0&limit=200"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}