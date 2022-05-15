package com.jewelry.domain.repository;

import org.springframework.stereotype.Repository;

import com.jewelry.domain.repository.mapper.UribaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UribaRepository {
	private final UribaMapper mapper;

}
