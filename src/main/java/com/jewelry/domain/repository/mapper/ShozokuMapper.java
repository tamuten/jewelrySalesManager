package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.jewelry.domain.model.Shozoku;

@Mapper
public interface ShozokuMapper {
	public long count();
	public List<Shozoku> findAll();
	public List<Shozoku> findPage(@Param("pageable") Pageable pageable);
	public Shozoku selectOne(int id);
	public int insertOne(Shozoku shozoku);
	public int updateOne(Shozoku shozoku);
	public int deleteOne(int id);
}
