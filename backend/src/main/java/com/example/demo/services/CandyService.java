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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CandyService {

    @Autowired
    private CandyRepository candyRepository;

    @Autowired
    private CrudLogService crudLogService;

    @Autowired
    private CandyMapper candyMapper;

  


    @Autowired
    private S3Service s3Service;


    public CandyResponseDTO createCandy(CandyRequestDTO candyRequestDTO, MultipartFile imageFile) {
        try {
            String imageKey = s3Service.uploadFile(imageFile);

            Candy candy = this.candyMapper.toEntity(candyRequestDTO);
            candy.setImageKey(imageKey);

            Candy saved = this.candyRepository.save(candy);

             List<Map<String, String>> candiesList = new ArrayList<>();

            Map<String, String> candyMap = getMappedCandy(saved);
            candiesList.add(candyMap);

            CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.CREATE.toString(), candiesList, Instant.now().toString());

           
            this.crudLogService.createLog(crudLogRequestDTO);

            return this.candyMapper.toResponseDTO(saved);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading the image", e);
        }
    }

    public List<CandyResponseDTO> getAllCandies(){
        List<Candy> candies = this.candyRepository.findAll();

        List<Map<String, String>> candiesList = new ArrayList<>();
  
        candies.forEach( candy ->{
            candiesList.add(
                getMappedCandy(candy)
            );
        });
        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toListResponseDTO(candies);
    }

    public List<CandyResponseDTO> getAllCakes(){
        List<Candy> candies=this.candyRepository.findByType(CandyType.CAKE);

        List<Map<String, String>> candiesList = new ArrayList<>();
        
        candies.forEach( candy ->{
            candiesList.add(
                getMappedCandy(candy)
            );
        });

        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toListResponseDTO(candies);
    }

    public List<CandyResponseDTO> getAllDocinhos(){
        List<Candy> candies=this.candyRepository.findByType(CandyType.DOCINHO);
        List<Map<String, String>> candiesList = new ArrayList<>();
        
        candies.forEach( candy ->{
            candiesList.add(
                getMappedCandy(candy)
            );
        });


        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toListResponseDTO(candies);
    }

    public List<CandyResponseDTO> getAllMuffins(){
        List<Candy> candies = this.candyRepository.findByType(CandyType.MUFFIN);
        List<Map<String, String>> candiesList = new ArrayList<>();
        
        candies.forEach( candy ->{
            candiesList.add(
                getMappedCandy(candy)
            );
        });


        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toListResponseDTO(candies);
    }

    public List<CandyResponseDTO> getAllBrownies(){
        List<Candy> candies=this.candyRepository.findByType(CandyType.BROWNIE);

        List<Map<String, String>> candiesList = new ArrayList<>();
        
         candies.forEach( candy ->{
            candiesList.add(
                getMappedCandy(candy)
            );
        });


        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toListResponseDTO(candies);
    }


    public CandyResponseDTO getCandyById(Long id){
        Candy candy=this.getCandy(id);

        List<Map<String, String>> candiesList = new ArrayList<>();
        
        candiesList.add(
            getMappedCandy(candy)
        );
      
        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return this.candyMapper.toResponseDTO(candy);
    }

    public void deleteCandyById(Long id) {
        List<Map<String, String>> candiesList = new ArrayList<>();
        Candy candy=this.getCandy(id);

        candiesList.add(
            getMappedCandy(candy)
        );

        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.DELETE.toString(), candiesList, Instant.now().toString());
     
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

        List<Map<String, String>> candiesList = new ArrayList<>();

        candiesList.add(
            getMappedCandy(candy)
        );
        CrudLogRequestDTO crudLogRequestDTO = new CrudLogRequestDTO(LogActions.UPDATE.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);
        return this.candyMapper.toResponseDTO(updated);
    }


    private Candy getCandy(Long id){
        return this.candyRepository.findById(id).orElseThrow(()-> new CandyNotFoundException());
    }

    private Map<String, String> getMappedCandy(Candy candy){
        return   Map.of(
            "id",candy.getId().toString(),
            "name",candy.getName(),
            "price",candy.getPrice().toString()
            );
    }
}
