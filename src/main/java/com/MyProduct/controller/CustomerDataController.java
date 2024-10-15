package com.MyProduct.controller;

import com.MyProduct.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/customers")
@Validated
@RequiredArgsConstructor
public class CustomerDataController {

    private final CustomerService customerService;

    @PostMapping("/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateReport(@Valid @NotBlank @RequestBody String inputData) {
        return ResponseEntity.ok(customerService.generateReport(inputData));
    }
}
