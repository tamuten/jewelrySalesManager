package com.jewelry.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.Tantosha;

@Mapper
public interface TantoshaMapper {
	public int insertOne(Tantosha tantosha);
}
