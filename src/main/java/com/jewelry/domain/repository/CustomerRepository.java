package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Customers;
import com.jewelry.domain.repository.mapper.CustomerMapper;

@Repository
public class CustomerRepository {
	@Autowired
	private CustomerMapper mapper;

	public List<Customers> findAll(){
		return mapper.findAll();
	}

	public Customers findByPk(Integer id) {
		return mapper.selectOne(id);
	}

	public int create(Customers customers) {
		return mapper.insertOne(customers);
	}

	public int update(Customers customers) {
		return mapper.updateOne(customers);
	}

	public int delete(Integer id) {
		return mapper.deleteOne(id);
	}
}
