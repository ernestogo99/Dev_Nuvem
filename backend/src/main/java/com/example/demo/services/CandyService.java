package com.example.demo.services;


import com.example.demo.domain.enums.CandyType;
import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.Candy;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.CandyNotFoundException;
import com.example.demo.infra.aws.s3.S3Service;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
            Candy candy = this.candyMapper.toEntity(candyRequestDTO);

            if(imageFile == null || imageFile.isEmpty()){
                throw new BadRequestException("Image file is required");
            }
            String imageKey = s3Service.uploadFile(imageFile);            
            candy.setImageKey(imageKey);

            Candy saved = this.candyRepository.save(candy);


            List<Map<String, String>> candiesList = new ArrayList<>();

            Map<String, String> candyMap = getMappedCandy(saved);
            candiesList.add(candyMap);

            CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.CREATE.toString(), candiesList, Instant.now().toString());

           
            this.crudLogService.createLog(crudLogRequestDTO);

            return mapCandyWithImageUrl(candy);
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
        
        return candies.stream()
                .map(this::mapCandyWithImageUrl)
                .collect(Collectors.toList());
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

        return candies.stream()
                .map(this::mapCandyWithImageUrl)
                .collect(Collectors.toList());
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

        return candies.stream()
            .map(this::mapCandyWithImageUrl)
            .collect(Collectors.toList());
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

        return candies.stream()
            .map(this::mapCandyWithImageUrl)
            .collect(Collectors.toList());
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

        return candies.stream()
            .map(this::mapCandyWithImageUrl)
            .collect(Collectors.toList());
    }


    public CandyResponseDTO getCandyById(Long id){
        Candy candy=this.getCandy(id);

        List<Map<String, String>> candiesList = new ArrayList<>();
        
        candiesList.add(
            getMappedCandy(candy)
        );
      
        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.READ.toString(), candiesList, Instant.now().toString());
        this.crudLogService.createLog(crudLogRequestDTO);

        return mapCandyWithImageUrl(candy);
    }

    public void deleteCandyById(Long id) {
        List<Map<String, String>> candiesList = new ArrayList<>();
        Candy candy=this.getCandy(id);

        candiesList.add(
            getMappedCandy(candy)
        );

        CrudLogRequestDTO crudLogRequestDTO= new CrudLogRequestDTO(LogActions.DELETE.toString(), candiesList, Instant.now().toString());
     
        this.crudLogService.createLog(crudLogRequestDTO);

        this.s3Service.deleteFile(candy.getImageKey());
        this.candyRepository.deleteById(id);
    }


    public CandyResponseDTO updateCandyById(Long id,CandyRequestDTO candyRequestDTO, MultipartFile file){
        Candy candy=this.getCandy(id);
        candy.setDescription(candyRequestDTO.description());
        candy.setName(candyRequestDTO.name());
        candy.setPrice(candyRequestDTO.price());
        candy.setType(candyRequestDTO.type());
        try{

            if(file != null && !file.isEmpty()){
                String newKey = s3Service.updateFile(file, candy.getImageKey());
                candy.setImageKey(newKey);   
            }
    
            Candy updated=this.candyRepository.save(candy);

            List<Map<String, String>> candiesList = new ArrayList<>();

            candiesList.add(
                getMappedCandy(candy)
            );
            CrudLogRequestDTO crudLogRequestDTO = new CrudLogRequestDTO(LogActions.UPDATE.toString(), candiesList, Instant.now().toString());
            this.crudLogService.createLog(crudLogRequestDTO);

            return mapCandyWithImageUrl(candy);
        } catch(IOException e){
            throw new RuntimeException("Error updating candy image for candy with id " + id, e);
        }
    }
        

    public Candy getCandy(Long id){
        return this.candyRepository.findById(id).orElseThrow(()-> new CandyNotFoundException());
    }

    public byte[] getImageFile(Long id)throws IOException{
        Candy candy = this.getCandy(id);
        return s3Service.downloadFile(candy.getImageKey());
    } 

    private Map<String, String> getMappedCandy(Candy candy){
        return   Map.of(
            "id",candy.getId().toString(),
            "name",candy.getName(),
            "price",candy.getPrice().toString()
            );
    }

    private CandyResponseDTO mapCandyWithImageUrl(Candy candy){
        CandyResponseDTO baseDto = candyMapper.toResponseDTO(candy);

        String imageUrl = s3Service.getFileUrl(candy.getImageKey());

        return new CandyResponseDTO(baseDto.id(), baseDto.name(), baseDto.price(),
         baseDto.description(), baseDto.type(), imageUrl);
    }
}
