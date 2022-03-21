package com.jewelry.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.repository.ShozokuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShozokuService {
	private final ShozokuRepository repository;

	public List<Shozoku> findAll() {
		return repository.findAll();
	}

	public Shozoku findByPk(int id) {
		return repository.findByPk(id);
	}

	public int create(Shozoku shozoku) {
		return repository.create(shozoku);
	}

	public int update(Shozoku shozoku) {
		return repository.update(shozoku);
	}

	public int delete(int id) {
		return repository.delete(id);
	}
}
