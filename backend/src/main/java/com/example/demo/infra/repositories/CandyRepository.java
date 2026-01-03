package com.example.demo.infra.repositories;

import com.example.demo.domain.enums.CandyType;
import com.example.demo.domain.model.Candy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("backend")
public interface CandyRepository extends JpaRepository<Candy,Long> {
    @Query("""
       SELECT c 
       FROM Candy c 
       WHERE c.type = :type
       """)
    List<Candy> findByType(@Param("type") CandyType type);

}
