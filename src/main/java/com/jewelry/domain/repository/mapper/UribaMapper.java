package com.jewelry.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.jewelry.domain.model.Uriba;

@Mapper
public interface UribaMapper {
	public List<Uriba> findAll();
	public List<Uriba> findPage(Pageable pageable);
	public long count();
	public Uriba select0ne(Integer id);
	public int insertOne(Uriba uriba);
	public int updateOne(Uriba uriba);
	public int deleteOne(Uriba uriba);
}
