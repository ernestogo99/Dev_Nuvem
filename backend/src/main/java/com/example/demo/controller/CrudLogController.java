package com.example.demo.controller;


import com.example.demo.services.CrudLogService;
import com.example.demo.shared.dto.response.CrudLogResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs")
public class CrudLogController {

    @Autowired
    private CrudLogService service;


    @GetMapping
    @Operation(description = """
            Get all crud logs
            """)
    public ResponseEntity<List<CrudLogResponseDTO>> getAllLogs(){
        return ResponseEntity.ok(this.service.getAllLogs());
    }
}
