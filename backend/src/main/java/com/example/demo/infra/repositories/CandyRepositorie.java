package com.example.demo.infra.repositories;

import com.example.demo.domain.model.Candy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandyRepositorie extends JpaRepository<Candy,Long> {
}
