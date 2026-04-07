package com.devsuperior.workshopcassandra.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopcassandra.model.dto.ProductDTO;
import com.devsuperior.workshopcassandra.model.entities.Product;
import com.devsuperior.workshopcassandra.repositories.ProductRepository;
import com.devsuperior.workshopcassandra.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public ProductDTO findById(UUID id) {
		Product Product = getById(id);
		return new ProductDTO(Product);
	}
	
	public List<ProductDTO> findByDepartment(String department) {
		List<Product> list;
		if ("".equals(department)) {
			list = repository.findAll();
		}
		else {
			list = repository.findByDepartment(department);
		}	
		return list.stream().map(x -> new ProductDTO(x)).toList();
	}
	
	private Product getById(UUID id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Not found"));
	}
}
