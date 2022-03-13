package com.jewelry.domain.repository;

import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.mapper.TantoshaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TantoshaRepository {
	private final TantoshaMapper mapper;

	public int create(Tantosha tantosha) {
		return mapper.insertOne(tantosha);
	}
}
