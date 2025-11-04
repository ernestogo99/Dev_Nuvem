package com.example.demo.services;


import com.example.demo.domain.enums.CandyType;
import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.Candy;
import com.example.demo.exceptions.CandyNotFoundException;
import com.example.demo.infra.repositories.CandyRepository;
import com.example.demo.shared.dto.request.CandyRequestDTO;
import com.example.demo.shared.dto.request.CrudLogRequestDTO;
import com.example.demo.shared.dto.response.CandyResponseDTO;
import com.example.demo.shared.mapper.CandyMapper;
import com.example.demo.shared.mapper.CrudLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CandyService {

    @Autowired
    private CandyRepository candyRepository;

    @Autowired
    private CrudLogService crudLogService;

    @Autowired
    private CandyMapper candyMapper;

    @Autowired
    private CrudLogMapper crudLogMapper;




    public CandyResponseDTO createCandy(CandyRequestDTO candyRequestDTO){
        Candy candy=this.candyMapper.toEntity(candyRequestDTO);
        Candy save=this.candyRepository.save(candy);
        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.CREATE,save.getId(),Instant.now());
        this.crudLogService.createLog(crudLogRequestDTO);
        return this.candyMapper.toResponseDTO(save);
    }

    public List<CandyResponseDTO> getAllCandies(){
        return this.candyMapper.toListResponseDTO(this.candyRepository.findAll());
    }

    public List<CandyResponseDTO> getAllCakes(){
        List<Candy> cakes=this.candyRepository.findByType(CandyType.CAKE);
        return this.candyMapper.toListResponseDTO(cakes);
    }

    public List<CandyResponseDTO> getAllDocinhos(){
        List<Candy> cakes=this.candyRepository.findByType(CandyType.DOCINHO);
        return this.candyMapper.toListResponseDTO(cakes);
    }

    public List<CandyResponseDTO> getAllMuffins(){
        List<Candy> cakes=this.candyRepository.findByType(CandyType.MUFFIN);
        return this.candyMapper.toListResponseDTO(cakes);
    }

    public List<CandyResponseDTO> getAllBrownies(){
        List<Candy> cakes=this.candyRepository.findByType(CandyType.BROWNIE);
        return this.candyMapper.toListResponseDTO(cakes);
    }


    public CandyResponseDTO getCandyById(Long id){
        Candy candy=this.getCandy(id);
        return this.candyMapper.toResponseDTO(candy);
    }

    public void deleteCandyById(Long id) {
        CrudLogRequestDTO crudLogRequestDTO =
                new CrudLogRequestDTO(LogActions.DELETE,id, Instant.now());

        this.crudLogService.createLog(crudLogRequestDTO);
        this.candyRepository.deleteById(id);
    }


    public CandyResponseDTO updateCandyById(Long id,CandyRequestDTO candyRequestDTO){
        Candy candy=this.getCandy(id);
        candy.setDescription(candyRequestDTO.description());
        candy.setName(candyRequestDTO.name());
        candy.setPrice(candyRequestDTO.price());
        candy.setType(candyRequestDTO.type());

        Candy updated=this.candyRepository.save(candy);
        CrudLogRequestDTO crudLogRequestDTO=new CrudLogRequestDTO(LogActions.UPDATE,updated.getId(),Instant.now());
        this.crudLogService.createLog(crudLogRequestDTO);
        return this.candyMapper.toResponseDTO(updated);
    }


    private Candy getCandy(Long id){
        return this.candyRepository.findById(id).orElseThrow(()-> new CandyNotFoundException());
    }
}
