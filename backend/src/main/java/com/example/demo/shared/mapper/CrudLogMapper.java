package com.example.demo.shared.mapper;


import com.example.demo.domain.model.Candy;
import com.example.demo.domain.model.CrudLog;
import com.example.demo.shared.dto.request.CrudLogRequestDTO;
import com.example.demo.shared.dto.response.CrudLogResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {Candy.class})
public interface CrudLogMapper {

    CrudLog toEntity(CrudLogRequestDTO crudLogRequestDTO);

    CrudLogRequestDTO toRequestDTO(CrudLog crudLog);

    CrudLogResponseDTO toResponseDTO(CrudLog crudLog);

    List<CrudLogResponseDTO> toListResponseDTO(List<CrudLog> crudLogs);
}
