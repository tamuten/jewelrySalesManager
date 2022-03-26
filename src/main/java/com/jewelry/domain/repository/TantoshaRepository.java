package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.mapper.TantoshaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TantoshaRepository {
	private final TantoshaMapper mapper;

	public long count(){
		return mapper.count();
	}

	public boolean existsByShozokuId(Integer shozokuId){
		return mapper.existsByShozokuId(shozokuId);
	}

	public List<Tantosha> findAll() {
		return mapper.findAll();
	}

	public List<Tantosha> findPage(Pageable pageable){
		return mapper.findPage(pageable);
	}

	public Tantosha findByPk(int id) {
		return mapper.selectOne(id);
	}

	public int create(Tantosha tantosha) {
		return mapper.insertOne(tantosha);
	}

	public int update(Tantosha tantosha) {
		return mapper.updateOne(tantosha);
	}

	public int delete(int id) {
		return mapper.deleteOne(id);
	}
}
