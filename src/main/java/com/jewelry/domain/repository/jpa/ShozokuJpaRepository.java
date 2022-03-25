package com.jewelry.domain.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.domain.model.Shozoku;

@Repository
public interface ShozokuJpaRepository extends JpaRepository<Shozoku, Integer> {
}
