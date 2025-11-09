package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/candies")
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
            @RequestPart("image") MultipartFile imageFile
    ) {
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

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a candy by id")
    public ResponseEntity<CandyResponseDTO> updateCandy(
            @PathVariable Long id,
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
}
