package com.oga.productsmanagment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.productsmanagment.dtos.ProductDTO;
import com.oga.productsmanagment.entity.Category;
import com.oga.productsmanagment.repository.CategoryRepository;
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
	@Autowired
	CategoryRepository categoryRepository;
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

		MvcResult result = mockMvc.perform(post("http://localhost:8084/api/v1/product/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productJson))
				.andExpect(status().isNotFound())
				.andReturn();


		String response = result.getResponse().getContentAsString();
		if (response!= null && !response.trim().isEmpty()) {
			ProductDTO savedProduct = objectMapper.readValue(response, ProductDTO.class);
			ID = savedProduct.getId();
		}else{
		}
	}
	@Test
	@Order(1)
	void testGetByIdProduct() throws Exception {
			mockMvc.perform(get("http://localhost:8084/api/v1/product/getById/"+ID));
	}
		@Test
		@Order(2)
		void testUpdateProduct() throws Exception {
		final String CAT_NAME = "testcat";
			Category category = new Category();

			categoryRepository.save(category);
			ProductDTO product = ProductDTO.builder()
					.qte(QTE)
					.name(NAME)
					.disponibilite(DISP)
					.categoryId(category.getId())
					.build();

			ObjectMapper objectMapper = new ObjectMapper();
			String productJson = objectMapper.writeValueAsString(product);

			MvcResult result= mockMvc.perform(post("http://localhost:8084/api/v1/product/create/"+ category.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(productJson)).andReturn();

			String response = result.getResponse().getContentAsString();
			ProductDTO savedProduct = objectMapper.readValue(response,ProductDTO.class);
			Long productId = savedProduct.getId();
			final int QTE_UPDATED = 20;

			ProductDTO updatedProduct = ProductDTO.builder()
					.id(productId)
					.qte(QTE_UPDATED)
					.categoryId((category.getId()))
					.build();

			mockMvc.perform(put("http://localhost:8084/api/v1/product/update")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedProduct)))
					.andExpect(status().isNotFound());





		}
	@Test
	@Order(3)
	void testDeleteProduct() throws Exception {

		mockMvc.perform(delete("http://localhost:8083/api/v1/product/delete/"+ ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}



}