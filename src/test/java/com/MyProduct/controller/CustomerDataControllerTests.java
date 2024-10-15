package com.MyProduct.controller;

import com.MyProduct.controller.CustomerDataController;
import com.MyProduct.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerDataController.class)
public class CustomerDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerDataService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void generateReport_withAdminRole_shouldReturnOk() throws Exception {
        String inputData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        mockMvc.perform(post("/api/v1/customers/report")
                        .content(inputData)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void generateReport_withUserRole_shouldReturnForbidden() throws Exception {
        String inputData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        mockMvc.perform(post("/api/v1/customers/report")
                        .content(inputData)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
