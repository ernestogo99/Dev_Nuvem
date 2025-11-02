package com.example.demo.domain.model;


import com.example.demo.domain.enums.LogActions;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrudLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private LogActions action;

    @Column(nullable = false)
    private Candy data;

    @Column(nullable = false)
    private Instant timeStamp;



}
