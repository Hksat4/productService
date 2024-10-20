package com.MyProduct.controller;

import com.MyProduct.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
@RequiredArgsConstructor
public class CustomerDataController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDataController.class);
    private final CustomerService customerService;

    @PostMapping("/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateReport(@Valid @NotBlank @RequestBody String inputData) {
        try {
            return ResponseEntity.ok(customerService.generateReport(inputData));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error generating report: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
