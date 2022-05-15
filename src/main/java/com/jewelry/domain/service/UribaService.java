package com.jewelry.domain.service;

import org.springframework.stereotype.Service;

import com.jewelry.domain.repository.UribaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UribaService {
	private final UribaRepository repository;

}
