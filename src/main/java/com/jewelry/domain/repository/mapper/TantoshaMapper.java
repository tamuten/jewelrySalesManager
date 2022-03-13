package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.Tantosha;

@Mapper
public interface TantoshaMapper {
	public List<Tantosha> findAll();
	public Tantosha selectOne(int id);
	public int insertOne(Tantosha tantosha);
	public int updateOne(Tantosha tantosha);
	public int deleteOne(int id);
}
