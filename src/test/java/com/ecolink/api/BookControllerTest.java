package com.ecolink.api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import org.json.JSONObject;
import org.json.JSONArray;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void postValidPayload() throws Exception{
        String jsonBody = """
            {
                "title": "Clean Code",
                "author": "Robert C. Martin",
                "publishedYear": 2008,
                "isbn": "978-0132350884"
            }
            """;

        mockMvc.perform(post("/api/books").
                contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)). //Making the request
                andExpect(status().isCreated()). //Checking the response
                andExpect(jsonPath("$.id").exists());
    }

    @Test
    void GetExistingBookWithCorrectFields() throws Exception{
        String jsonBody = """
            {
                "title": "Clean Code",
                "author": "Robert C. Martin",
                "publishedYear": 2008,
                "isbn": "978-0132350884"
            }
            """;

        //Start by making a book:
        String mvcResult = mockMvc.perform(post("/api/books").
                contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)). //Making the request
                andExpect(status().isCreated()). //Checking the response
                andExpect(jsonPath("$.id").exists()).andReturn().getResponse().getContentAsString();

        //Here i got the json in the mvcresult string:
        JSONObject object = new JSONObject(mvcResult);
        String id = object.getString("id");


        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(status().isOk());
    }

    @Test
    void getNonexistingBook() throws Exception{
        mockMvc.perform(get("/api/books/{id}", 9999999))
                .andExpect(status().isNotFound());
    }
}
