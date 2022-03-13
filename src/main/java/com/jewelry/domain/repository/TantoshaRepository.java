package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.mapper.TantoshaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TantoshaRepository {
	private final TantoshaMapper mapper;

	public List<Tantosha> findAll() {
		return mapper.findAll();
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
