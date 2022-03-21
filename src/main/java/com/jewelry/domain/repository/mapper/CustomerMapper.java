package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.Customers;

@Mapper
public interface CustomerMapper {
	public List<Customers> findAll();
	public Customers selectOne(Integer id);
	public int insertOne(Customers customers);
	public int updateOne(Customers customers);
	public int deleteOne(Integer id);
}
