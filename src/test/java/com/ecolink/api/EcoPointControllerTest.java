package com.ecolink.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EcoPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getPage1() throws Exception{
        mockMvc.perform(get("/api/ecopoints?page=1&limit=10"))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.pagination.page").value(1));
    }


    @Test
    @WithMockUser
    void getPage2() throws Exception{
        mockMvc.perform(get("/api/ecopoints?page=2&limit=10"))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.pagination.page").value(2));
    }

    @Test
    @WithMockUser
    void getLastPage() throws Exception{
        mockMvc.perform(get("/api/ecopoints?page=1&limit=50"))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.pagination.page").value(1));
    }

    @Test
    @WithMockUser
void getWithNoLatLngValuesFallback() throws Exception {
    mockMvc.perform(get("/api/ecopoints")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.pagination.page").value(1))
            .andExpect(jsonPath("$.pagination.limit").value(10));
}

}
