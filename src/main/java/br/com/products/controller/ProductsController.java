package br.com.products.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.products.model.ProductsModel;
import br.com.products.model.ResponseModel;
import br.com.products.service.ProductsService;

@RestController
public class ProductsController {

	@Autowired
	private ProductsService ps;
	
	@GetMapping("/health")
	public String health() {
		return "API OK - SERVER UP - WORKING";
	}
	
	@GetMapping("/listar")
	public Iterable<ProductsModel> listar(){
		return ps.listar();
	}
	
	@GetMapping("/listar/{id}")
	public Optional<ProductsModel> listarById(@PathVariable long id){
		return ps.listarById(id);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody ProductsModel pm){
		return ps.cadastrarAlterar(pm, "cadastrar");
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody ProductsModel pm){
		return ps.cadastrarAlterar(pm, "alterar");
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<ResponseModel> deletar(@PathVariable long id){
		return ps.deletar(id);
	}
}
