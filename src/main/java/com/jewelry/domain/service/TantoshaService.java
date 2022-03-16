package com.jewelry.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.TantoshaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TantoshaService {
	private final TantoshaRepository repository;

	public List<Tantosha> findAll() {
		return repository.findAll();
	}

	public Tantosha findByPk(int id) {
		return repository.findByPk(id);
	}

	public int create(Tantosha tantosha) {
		return repository.create(tantosha);
	}

	public int update(Tantosha tantosha) {
		return repository.update(tantosha);
	}

	public int delete(int id) {
		return repository.delete(id);
	}
}