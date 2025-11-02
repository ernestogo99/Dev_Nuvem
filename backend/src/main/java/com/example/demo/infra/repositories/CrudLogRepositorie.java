package com.example.demo.infra.repositories;

import com.example.demo.domain.model.CrudLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudLogRepositorie extends JpaRepository<CrudLog,Long> {
}
