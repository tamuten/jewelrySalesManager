package com.jewelry.domain.service;

import java.util.Collections;
import java.util.List;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.repository.TantoshaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TantoshaService {
	private final TantoshaRepository repository;

	public List<Tantosha> findAll() {
		return repository.findAll();
	}

	public Page<Tantosha> findPage(Pageable pageable) {
		long tantoshaCnt = repository.count();
		List<Tantosha> tantoshaList = Collections.emptyList();

		if (tantoshaCnt > 0){
			tantoshaList = repository.findPage(pageable);
		}

		log.info("[TantoshaList] " + tantoshaList.toString());

		return new PageImpl<Tantosha>(tantoshaList, pageable, tantoshaCnt);
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
