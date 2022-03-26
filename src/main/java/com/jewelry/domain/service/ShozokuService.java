package com.jewelry.domain.service;

import java.util.Collections;
import java.util.List;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.repository.ShozokuRepository;
import com.jewelry.domain.repository.TantoshaRepository;
import com.jewelry.exception.ForeignKeyConstraintViolationException;

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
public class ShozokuService {
	private final ShozokuRepository repository;
	private final TantoshaRepository tantoshaRepository;

	public List<Shozoku> findAll() {
		return repository.findAll();
	}

	public Page<Shozoku> findPage(Pageable pageable) {
		long shozokuCnt = repository.count();
		List<Shozoku> shozokuList = Collections.emptyList();

		if (shozokuCnt > 0) {
			shozokuList = repository.findPage(pageable);
		}

		log.info("[ShozokuList] " + shozokuList.toString());

		return new PageImpl<Shozoku>(shozokuList, pageable, shozokuCnt);
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
		if(tantoshaRepository.existsByShozokuId(id)){
            throw new ForeignKeyConstraintViolationException("some tantosha has shozoku_id = " + String.valueOf(id));
		}
		return repository.delete(id);
	}
}
