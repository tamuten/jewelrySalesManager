package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.domain.model.Shozoku;

@Mapper
public interface ShozokuMapper {
	public List<Shozoku> findAll();
	public Shozoku selectOne(int id);
	public int insertOne(Shozoku shozoku);
	public int updateOne(Shozoku shozoku);
	public int deleteOne(int id);
}
