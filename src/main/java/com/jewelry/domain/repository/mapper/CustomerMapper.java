package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.Customer;

@Mapper
public interface CustomerMapper {
	public List<Customer> findAll();
	public Customer selectOne(Integer id);
	public int insertOne(Customer customers);
	public int updateOne(Customer customers);
	public int deleteOne(Integer id);
}
