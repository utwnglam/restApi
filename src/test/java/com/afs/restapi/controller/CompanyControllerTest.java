package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
  public void cleanRepository() {
    companyRepository.clearAll();
  }

  @Test
  public void should_get_all_companies_when_GET_given_companies() throws Exception {
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
  public void should_return_company_when_perform_get_given_id() throws Exception {
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

  @Test
  public void should_return_company_when_perform_get_given_page_and_page_size() throws Exception {
    //given
    companyRepository.create(new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    ));
    companyRepository.create(new Company(2, "comm2",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    ));
    //when
    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE + "?page=1&pageSize=2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[*].companyName").value(containsInAnyOrder("comm", "comm2")))
      .andExpect(jsonPath("$[0].employees", hasSize(2)))
      .andExpect(jsonPath("$[1].employees", hasSize(2)));
  }

  @Test
  public void should_create_company_when_POST_given_company() throws Exception {
    String companyJsonString = "{\n" +
      "        \"id\": 1,\n" +
      "        \"companyName\": \"cannotFindCompany222\",\n" +
      "        \"employees\": [\n" +
      "            {\n" +
      "                \"id\": 1,\n" +
      "                \"name\": \"who3\",\n" +
      "                \"age\": 20,\n" +
      "                \"gender\": \"Male\",\n" +
      "                \"salary\": 1500\n" +
      "            },\n" +
      "            {\n" +
      "                \"id\": 2,\n" +
      "                \"name\": \"dearjane\",\n" +
      "                \"age\": 29,\n" +
      "                \"gender\": \"Female\",\n" +
      "                \"salary\": 10000\n" +
      "            }\n" +
      "        ]\n" +
      "    }";

    mockMvc.perform(MockMvcRequestBuilders.post(COMPANIES_URL_BASE)
        .contentType(MediaType.APPLICATION_JSON)
        .content(companyJsonString))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").isNumber())
      .andExpect(jsonPath("$.companyName").value("cannotFindCompany222"))
      .andExpect(jsonPath("$.employees", hasSize(2)))
      .andExpect(jsonPath("$.employees[*].name").value(containsInAnyOrder("who3", "dearjane")))
      .andExpect(jsonPath("$.employees[*].age").value(containsInAnyOrder(20, 29)))
      .andExpect(jsonPath("$.employees[*].gender").value(containsInAnyOrder("Female", "Male")))
      .andExpect(jsonPath("$.employees[*].salary").value(containsInAnyOrder(10000, 1500)));
  }

  @Test
  public void should_return_edited_company_when_put_given_updated_employee() throws Exception {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    companyRepository.create(company);

    String updatedCompanyJson = "{\n" +
      "        \"id\": 1,\n" +
      "        \"companyName\": \"cannotFindCompany222\",\n" +
      "        \"employees\": [\n" +
      "            {\n" +
      "                \"id\": 1,\n" +
      "                \"name\": \"who3\",\n" +
      "                \"age\": 20,\n" +
      "                \"gender\": \"Male\",\n" +
      "                \"salary\": 1500\n" +
      "            },\n" +
      "            {\n" +
      "                \"id\": 2,\n" +
      "                \"name\": \"dearjane\",\n" +
      "                \"age\": 29,\n" +
      "                \"gender\": \"Female\",\n" +
      "                \"salary\": 10000\n" +
      "            }\n" +
      "        ]\n" +
      "    }";

    mockMvc.perform(MockMvcRequestBuilders.put(COMPANIES_URL_BASE + "/" + company.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedCompanyJson))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.companyName").value("cannotFindCompany222"));
  }

  @Test
  void should_delete_when_perform_delete_given_company_id() throws Exception {
    //given
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    companyRepository.create(company);

    mockMvc.perform(MockMvcRequestBuilders.delete(COMPANIES_URL_BASE + "/" + company.getId()))
      .andExpect(status().isNoContent());

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(0)));
  }
}
