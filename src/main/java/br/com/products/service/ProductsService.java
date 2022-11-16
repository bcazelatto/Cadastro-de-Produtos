package br.com.products.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.products.model.ProductsModel;
import br.com.products.model.ResponseModel;
import br.com.products.repositories.ProductsRepository;

@Service
public class ProductsService {
	
	@Autowired
	private ProductsRepository pr;
	
	@Autowired
	private ResponseModel rm;
	
	public Iterable<ProductsModel> listar(){
		return pr.findAll();
	}
	
	public Optional<ProductsModel> listarById(long id) {
		return pr.findById(id);
	}
	
	public ResponseEntity<?> cadastrarAlterar(ProductsModel pm, String status) {

			if(status.equals("alterar") && pm.getCode() == null) { 
				rm.setMessage("O código do produto é obrigatório!");
				return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
			}else if (status.equals("cadastrar") && pm.getCode() != null){
				rm.setMessage("Não é necessário informar o código do produto!");
				return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
			}else if(pm.getName() == null || pm.getName().isEmpty() ) {
				rm.setMessage("O nome do produto é obrigatório!");
				return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
			}else if(pm.getBrand() == null || pm.getBrand().isEmpty()){
				rm.setMessage("A marca do produto é obrigatório!");
				return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
			}else {
				if(status.equals("cadastrar")) {
					pr.save(pm);
					rm.setMessage("Cadastrado com sucesso!");
					return new ResponseEntity<>(rm, HttpStatus.CREATED);
				}else {
					pr.save(pm);
					rm.setMessage("Alterado com sucesso!");
					return new ResponseEntity<>(rm, HttpStatus.OK);
				}
			}
		
	}
	
	public ResponseEntity<ResponseModel> deletar(long id){
		
			if(pr.findById(id).isEmpty()) {
				rm.setMessage("Produto não existe na tabela para ser deletado!");
				return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
			}
			
			pr.deleteById(id);
			rm.setMessage("Deletado com sucesso!");
			return new ResponseEntity<>(rm, HttpStatus.OK);

	}

}
