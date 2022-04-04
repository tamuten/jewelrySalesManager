package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.jewelry.domain.model.Sales;

@Mapper
public interface SalesMapper {
	public long count();
	public long countByCustomer(Integer customerId);
	public List<Sales> findAll();
	public List<Sales> findPage(@Param("pageable") Pageable pageable);
	public List<Sales> findByCustomer(@Param("pageable") Pageable pageable, Integer customerId);
	public Sales selectOne(Integer id);
	public int insertOne(Sales sales);
	public int insertBulk(List<Sales> salesList);
	public int updateOne(Sales sales);
	public int deleteOne(Integer id);
}
