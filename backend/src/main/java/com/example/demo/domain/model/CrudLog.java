package com.example.demo.domain.model;


import com.example.demo.domain.enums.LogActions;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Enumerated(EnumType.STRING)
    private LogActions action;


    @Column(name = "candy_id")
    private Long candyId;


    @Column(nullable = false)
    private Instant timeStamp;



}
