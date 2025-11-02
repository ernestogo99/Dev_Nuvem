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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candies")
@Tag(name = "Candies")
@RequiredArgsConstructor
public class CandyController {

    private final CandyService candyService;


    @PostMapping
    @Operation(
            summary = "Create a new candy",
            description = "Creates a candy and also stores a log entry in DynamoDB"
    )
    public ResponseEntity<CandyResponseDTO> createCandy(
            @RequestBody @Valid CandyRequestDTO candyRequestDTO
    ) {
        CandyResponseDTO response = candyService.createCandy(candyRequestDTO);
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


    @PutMapping("/{id}")
    @Operation(summary = "Update a candy by ID")
    public ResponseEntity<CandyResponseDTO> updateCandy(
            @Parameter(description = "ID of the candy") @PathVariable Long id,
            @RequestBody @Valid CandyRequestDTO dto
    ) {
        CandyResponseDTO response = candyService.updateCandyById(id, dto);
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
