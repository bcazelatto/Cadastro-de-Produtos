package br.com.products.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.products.model.ProductsModel;
import br.com.products.repositories.ProductsRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	ProductsRepository productsRepository;
	
	@Test
	public void apiHealthTestSucess() throws Exception {
		mockMvc.perform(get("/health"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void productsTestGetAll() throws Exception {
		mockMvc.perform(get("/listar"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void productsTestGetById() throws Exception {
		mockMvc.perform(get("/listar/{id}", 1))
			.andExpect(status().isOk());
	}
	
	@Test
	public void productsTestSaveSucess() throws Exception {
		
		ProductsModel pm = new ProductsModel();
		pm.setName("nameTest");
		pm.setBrand("brandTest");
		
		mockMvc.perform(post("/cadastrar") // Inserir post e o endpoint solicitado
				.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
				.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
				.andExpect(status().isCreated()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestSaveWithCode() throws Exception {
		
		ProductsModel pm = new ProductsModel();
		pm.setCode((long)1);
		pm.setName("nameTest");
		pm.setBrand("brandTest");
		
		mockMvc.perform(post("/cadastrar") // Inserir post e o endpoint solicitado
				.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
				.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
				.andExpect(status().isBadRequest()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestSaveWithoutName() throws Exception {
		
		ProductsModel pm = new ProductsModel();
		pm.setBrand("brandTest");
		
		mockMvc.perform(post("/cadastrar") // Inserir post e o endpoint solicitado
				.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
				.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
				.andExpect(status().isBadRequest()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestSaveWithoutBrand() throws Exception {
		
		ProductsModel pm = new ProductsModel();
		pm.setName("nameTest");
		
		mockMvc.perform(post("/cadastrar") // Inserir post e o endpoint solicitado
				.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
				.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
				.andExpect(status().isBadRequest()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestUpdateSucess() throws Exception {
		ProductsModel pm = new ProductsModel();
		pm.setCode((long)1);
		pm.setName("nameTest");
		pm.setBrand("brandTest");
		
		mockMvc.perform(put("/alterar") // Inserir put e o endpoint solicitado
				.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
				.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
				.andExpect(status().isOk()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestUpdateWithoutCode() throws Exception {
	ProductsModel pm = new ProductsModel();
	pm.setName("nameTest");
	pm.setBrand("brandTest");
	
	mockMvc.perform(put("/alterar") // Inserir put e o endpoint solicitado
			.contentType("application/json") //Inserir sempre para o cabeçalho da requisição
			.content(mapper.writeValueAsString(pm))) //Tranformar o objeto ProductsModel 'pm' em json via objectMapper
			.andExpect(status().isBadRequest()); // Inserir o que espera dessa requisição
		
	}
	
	@Test
	public void productsTestDeleteNotFound() throws Exception {
		ProductsModel pm = new ProductsModel();
		
		mockMvc.perform(delete("/deletar/{id}", 1)
				.contentType("application/json")
				.content(mapper.writeValueAsString(pm)))
				.andExpect(status().isNotFound()); 
		
	}
	
	@Test
	public void productsTestDeleteSucess() throws Exception {

		
		Optional<ProductsModel> optionalUser = Optional.of(new ProductsModel());
		
		when(productsRepository.findById((long) 1)).thenReturn(optionalUser);
		
		mockMvc.perform(delete("/deletar/{id}", 1)
				.contentType("application/json")
				.content(mapper.writeValueAsString(optionalUser)))
				.andExpect(status().isOk()); 
		
	}


}
