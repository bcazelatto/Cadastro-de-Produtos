package br.com.products.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.products.model.ProductsModel;

@Repository
public interface ProductsRepository extends CrudRepository<ProductsModel, Long> {

}
