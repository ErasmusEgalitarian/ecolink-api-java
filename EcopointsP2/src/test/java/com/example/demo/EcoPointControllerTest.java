package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class EcoPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPage1() throws Exception{
        mockMvc.perform(get("/api/ecopoint/"))
    }


    @Test
    void getPage2() throws Exception{

    }

    @Test
    void getLastPage() throws Exception{

    }

    @Test
    void getWithNoLatLngValuesFallback() throws Exception {

    }

}
