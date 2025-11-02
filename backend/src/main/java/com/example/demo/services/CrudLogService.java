package com.example.demo.services;

import com.example.demo.domain.model.CrudLog;
import com.example.demo.infra.repositories.CrudLogRepository;
import com.example.demo.shared.dto.request.CrudLogRequestDTO;
import com.example.demo.shared.dto.response.CrudLogResponseDTO;
import com.example.demo.shared.mapper.CrudLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudLogService {

    @Autowired
    private CrudLogRepository crudLogRepository;

    @Autowired
    private CrudLogMapper mapper;


    public CrudLogResponseDTO createLog(CrudLogRequestDTO requestDTO){
        CrudLog log= this.mapper.toEntity(requestDTO);
        CrudLog save=this.crudLogRepository.save(log);
        return this.mapper.toResponseDTO(save);
    }

    public List<CrudLogResponseDTO> getAllLogs(){
        return this.mapper.toListResponseDTO(this.crudLogRepository.findAll());
    }
}
