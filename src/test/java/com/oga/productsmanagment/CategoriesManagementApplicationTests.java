package com.oga.productsmanagment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.productsmanagment.dtos.CategoryDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriesManagementApplicationTests {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generate(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }
    static final String NAME = generate(8);
    static final int QTE =2001;
    //			Integer.parseInt(String.valueOf(Math.round(Math.random() * 100)));
    static final boolean DATEDECREATION =true;

    static final boolean DATEDEMODIFICATION =true;

    static Long ID = null;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(0)
    void testAddCategory() throws Exception {
        CategoryDTO category = CategoryDTO.builder()
                .qte(QTE)
                .name(NAME)
                .dateDeCreation(LocalDate.now())
                .dateDeModification(LocalDate.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryJson = objectMapper.writeValueAsString(category);

        MvcResult result = mockMvc.perform(post("http://localhost:8084/api/v1/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        CategoryDTO savedCategory = objectMapper.readValue(response, CategoryDTO.class);
        ID = savedCategory.getId();

    }

    @Test
    @Order(1)
    void testGetByIdCategory() throws Exception {
        mockMvc.perform(get("http://localhost:8084/api/v1/category/getById/"+ID)).andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void testUpdateCategory() throws Exception {
        CategoryDTO category = CategoryDTO.builder()
                .id(ID)
                .qte(123455)
                .name(NAME)
                .dateDeCreation(LocalDate.now())
                .dateDeModification(LocalDate.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryJson = objectMapper.writeValueAsString(category);
        mockMvc.perform(put("http://localhost:8084/api/v1/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isNotFound());


    }


    @Test
    @Order(3)
    void testDeleteCategory() throws Exception {

        mockMvc.perform(delete("http://localhost:8084/api/v1/category/delete/"+ ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
