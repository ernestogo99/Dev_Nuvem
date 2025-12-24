package com.example.demo.controller;

import com.example.demo.domain.model.Candy;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.CandyNotFoundException;
import com.example.demo.services.CandyService;
import com.example.demo.shared.dto.request.CandyRequestDTO;
import com.example.demo.shared.dto.response.CandyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;

@RestController
@RequestMapping("/api/candies")
@Tag(name = "Candies")
@RequiredArgsConstructor
public class CandyController {

    private final CandyService candyService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Create a new candy",
            description = "Creates a candy and also stores a log entry in DynamoDB"
    )
    public ResponseEntity<CandyResponseDTO> createCandy(
            @RequestPart("candy") @Valid CandyRequestDTO candyRequestDTO,
            @RequestPart("image") MultipartFile imageFile,
            BindingResult bindingResult) throws BadRequestException{

        if(bindingResult.hasErrors()){
            throw new BadRequestException("Failed to create product. Check if all fields are valid.", bindingResult);
        }
        CandyResponseDTO response = candyService.createCandy(candyRequestDTO, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all candies")
    public ResponseEntity<List<CandyResponseDTO>> getAllCandies() {
        return ResponseEntity.ok(candyService.getAllCandies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a candy by ID")
    public ResponseEntity<CandyResponseDTO> getCandyById(
            @Parameter(description = "ID of the candy") @PathVariable Long id
    ) {
        return ResponseEntity.ok(candyService.getCandyById(id));
    }

  
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a candy by ID")
    public ResponseEntity<CandyResponseDTO> updateCandy(
        @Parameter(description = "ID of the candy") @PathVariable Long id,
        @RequestPart("candy") @Valid CandyRequestDTO dto,
        @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        CandyResponseDTO response = candyService.updateCandyById(id, dto, imageFile);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a candy by ID")
    public ResponseEntity<Void> deleteCandy(
            @Parameter(description = "ID of the candy") @PathVariable Long id
    ) {
        candyService.deleteCandyById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/cakes")
    @Operation(summary = "Get all candies of type CAKE")
    public ResponseEntity<List<CandyResponseDTO>> getAllCakes() {
        return ResponseEntity.ok(candyService.getAllCakes());
    }

    @GetMapping("/docinhos")
    @Operation(summary = "Get all candies of type DOCINHO")
    public ResponseEntity<List<CandyResponseDTO>> getAllDocinhos() {
        return ResponseEntity.ok(candyService.getAllDocinhos());
    }

    @GetMapping("/muffins")
    @Operation(summary = "Get all candies of type MUFFIN")
    public ResponseEntity<List<CandyResponseDTO>> getAllMuffins() {
        return ResponseEntity.ok(candyService.getAllMuffins());
    }

    @GetMapping("/brownies")
    @Operation(summary = "Get all candies of type BROWNIE")
    public ResponseEntity<List<CandyResponseDTO>> getAllBrownies() {
        return ResponseEntity.ok(candyService.getAllBrownies());
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCandyImage(@PathVariable Long id) throws IOException{
        try {
            byte[] imageData = candyService.getImageFile(id);
            Candy candy = candyService.getCandy(id);
            MediaType contentType = determineContentType(candy.getImageKey());
                return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(imageData);
                    
        } catch (CandyNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private MediaType determineContentType(String imageKey) {
        if (imageKey == null) return MediaType.ALL;
        
        String lowerKey = imageKey.toLowerCase();
        if (lowerKey.endsWith(".png")) return MediaType.IMAGE_PNG;
        return MediaType.IMAGE_JPEG;
    }
}

