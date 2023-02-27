package com.oga.productsmanagment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.productsmanagment.dtos.ProductDTO;
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

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductsManagementApplicationTests {

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
	static final boolean DISP = true;
	static Long ID = null;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(0)
	void testAddProduct() throws Exception {
		ProductDTO product = ProductDTO.builder()
				.qte(QTE)
				.name(NAME)
				.disponibilite(DISP)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String productJson = objectMapper.writeValueAsString(product);

		MvcResult result = mockMvc.perform(post("http://localhost:8083/api/v1/product/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productJson))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		ProductDTO savedProduct = objectMapper.readValue(response, ProductDTO.class);
		ID = savedProduct.getId();

	}
	@Test
	@Order(1)
	void testGetByIdProduct() throws Exception {
			mockMvc.perform(get("http://localhost:8083/api/v1/product/getById/"+ID)).andExpect(status().isOk());
	}
		@Test
		@Order(2)
		void testUpdateProduct() throws Exception {
			ProductDTO product = ProductDTO.builder()
					.qte(QTE)
					.name(NAME)
					.disponibilite(DISP)
					.build();

			ObjectMapper objectMapper = new ObjectMapper();
			String productJson = objectMapper.writeValueAsString(product);

			 mockMvc.perform(put("http://localhost:8083/api/v1/product/update")
							.contentType(MediaType.APPLICATION_JSON)
							.content(productJson))
					.andExpect(status().isOk());



		}
	@Test
	@Order(3)
	void testDeleteProduct() throws Exception {

		mockMvc.perform(delete("http://localhost:8083/api/v1/product/delete/"+ ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}



}