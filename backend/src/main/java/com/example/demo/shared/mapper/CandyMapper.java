package com.example.demo.shared.mapper;


import com.example.demo.domain.model.Candy;
import com.example.demo.shared.dto.request.CandyRequestDTO;
import com.example.demo.shared.dto.response.CandyResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandyMapper{ 

    Candy toEntity(CandyRequestDTO candyRequestDTO);

    CandyRequestDTO toRequestDTO(Candy candy);

    CandyResponseDTO toResponseDTO(Candy candy);

    List<CandyResponseDTO> toListResponseDTO(List<Candy> candy);
}
