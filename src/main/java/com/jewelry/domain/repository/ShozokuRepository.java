package com.jewelry.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.repository.mapper.ShozokuMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShozokuRepository {
	private final ShozokuMapper mapper;

	public long count() {
		return mapper.count();
	}

	public List<Shozoku> findAll() {
		return mapper.findAll();
	}

	public List<Shozoku> findPage(Pageable pageable) {
		return mapper.findPage(pageable);
	}

	public Shozoku findByPk(int id) {
		return mapper.selectOne(id);
	}

	public int create(Shozoku shozoku) {
		return mapper.insertOne(shozoku);
	}

	public int update(Shozoku shozoku) {
		return mapper.updateOne(shozoku);
	}

	public int delete(int id) {
		return mapper.deleteOne(id);
	}
}
