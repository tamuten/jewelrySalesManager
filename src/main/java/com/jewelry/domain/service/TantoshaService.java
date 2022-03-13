package com.jewelry.domain.service;

import org.springframework.stereotype.Service;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.TantoshaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TantoshaService {
	private final TantoshaRepository repository;

	public int create(Tantosha tantosha) {
		return repository.create(tantosha);
	}
}
