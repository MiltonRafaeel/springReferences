package com.devsuperior.workshopcassandra.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopcassandra.model.dto.DepartmentDTO;
import com.devsuperior.workshopcassandra.model.entities.Department;
import com.devsuperior.workshopcassandra.repositories.DepartmentRepository;
import com.devsuperior.workshopcassandra.services.exceptions.ResourceNotFoundException;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;
	
	public List<DepartmentDTO> findAll() {
		List<Department> list = repository.findAll();
		return list.stream().map(x -> new DepartmentDTO(x)).toList();
	}
	
	public DepartmentDTO findById(UUID id) {
		Optional<Department> obj = repository.findById(id);
		Department department = obj.orElseThrow(() -> new ResourceNotFoundException("Not found"));
		return new DepartmentDTO(department);
	}
	
	public DepartmentDTO insert (DepartmentDTO obj) {
		Department entity = new Department();
		entity.setId(UUID.randomUUID());
		copyDtoToEntity(obj, entity);
		entity = repository.save(entity);
		return new DepartmentDTO(entity);
	}
	
	private void copyDtoToEntity(DepartmentDTO dto, Department entity) {
		entity.setName(dto.getName());
	}
	
}
