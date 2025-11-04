package com.example.demo.infra.repositories;

import com.example.demo.domain.model.CrudLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudLogRepository extends JpaRepository<CrudLog,Long> {
}
