package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
  public static final String COMPANIES_URL_BASE = "/companies";
  @Autowired
  MockMvc mockMvc;
  @Autowired
  CompanyRepository companyRepository;

  @BeforeEach
  void cleanRepository() {
    companyRepository.clearAll();
  }

  @Test
  public void should_get_all_employees_when_GET_given_employee() throws Exception {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    companyRepository.create(company);

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[0].companyName").value("comm"))
      .andExpect(jsonPath("$[0].employees[*].name").value(containsInAnyOrder("Terence", "wh")))
      .andExpect(jsonPath("$[0].employees[*].age").value(containsInAnyOrder(20, 29)))
      .andExpect(jsonPath("$[0].employees[*].gender").value(containsInAnyOrder("Female", "Male")))
      .andExpect(jsonPath("$[0].employees[*].salary").value(containsInAnyOrder(10000, 66666)));
  }

  @Test
  void should_return_company_when_perform_get_given_id() throws Exception {
    //given
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    companyRepository.create(company);

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE + "/" + company.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").isNumber())
      .andExpect(jsonPath("$.companyName").value("comm"))
      .andExpect(jsonPath("$.employees[*].name").value(containsInAnyOrder("Terence", "wh")))
      .andExpect(jsonPath("$.employees[*].age").value(containsInAnyOrder(20, 29)))
      .andExpect(jsonPath("$.employees[*].gender").value(containsInAnyOrder("Female", "Male")))
      .andExpect(jsonPath("$.employees[*].salary").value(containsInAnyOrder(10000, 66666)));
  }

  
}
