package com.MyProduct.controller;

import com.MyProduct.config.TestConfig; // Import your TestConfig
import com.MyProduct.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerDataController.class)
@Import(TestConfig.class) // Import your test configuration
public class CustomerDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void generateReport_withAdminRole_shouldReturnOk() throws Exception {
        String inputData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        Map<String, Object> reportResponse = Map.of("status", "success", "message", "Report generated for: " + inputData);

        // Mock the service response
        when(customerService.generateReport(inputData)).thenReturn(reportResponse);

        mockMvc.perform(post("/api/v1/customers/report")
                        .content(inputData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Expecting 200 OK response
    }

    @Test
    @WithMockUser(roles = "USER") // Simulating a user with USER role
    public void generateReport_withUserRole_shouldReturnForbidden() throws Exception {
        String inputData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";

        mockMvc.perform(post("/api/v1/customers/report")
                        .content(inputData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden response
    }
}
