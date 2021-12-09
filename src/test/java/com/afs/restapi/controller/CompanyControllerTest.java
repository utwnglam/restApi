package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryInMongo;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
  EmployeeRepositoryInMongo employeeRepository;
  @Autowired
  CompanyRepository companyRepository;
  @Autowired
  CompanyRepositoryInMongo companyRepositoryInMongo;


  @BeforeEach
  public void cleanRepository() {
    companyRepository.clearAll();
    companyRepositoryInMongo.deleteAll();
    employeeRepository.deleteAll();
  }

  @Test
  public void should_get_all_companies_when_GET_given_companies() throws Exception {
    Company company = new Company("1", "comm");
    companyRepositoryInMongo.insert(company);

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id").isString())
      .andExpect(jsonPath("$[0].companyName").value("comm"));
  }

  @Test
  public void should_return_company_when_perform_get_given_id() throws Exception {
    //given
    Company company = new Company(null, "comm");
    companyRepositoryInMongo.insert(company);

    employeeRepository.insert(new Employee(null, "Terence", 29, "Male", 66666, company.getId()));
    employeeRepository.insert(new Employee(null, "Terence2", 28, "Male", 66666, company.getId()));
    employeeRepository.insert(new Employee(null, "Terence3", 27, "Male", 66666, "2"));

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE + "/" + company.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").isString())
      .andExpect(jsonPath("$.companyName").value("comm"))
      .andExpect(jsonPath("$.employees", hasSize(2)))
      .andExpect(jsonPath("$.employees[*].name").value(containsInAnyOrder("Terence", "Terence2")))
      .andExpect(jsonPath("$.employees[*].age").value(containsInAnyOrder(28, 29)))
      .andExpect(jsonPath("$.employees[*].gender").value(containsInAnyOrder("Male", "Male")));
    ;
  }

  @Test
  void should_return_employees_when_perform_get_given_company_id() throws Exception {
    //given
    Company company = new Company(null, "comm");
    companyRepositoryInMongo.insert(company);

    employeeRepository.insert(new Employee(null, "Terence", 29, "Male", 66666, company.getId()));
    employeeRepository.insert(new Employee(null, "Terence2", 28, "Male", 66666, company.getId()));
    employeeRepository.insert(new Employee(null, "Terence3", 27, "Male", 66666, "2"));

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE + "/" + company.getId() + "/employees"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(2)))
      .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("Terence", "Terence2")))
      .andExpect(jsonPath("$[*].age").value(containsInAnyOrder(29, 28)))
      .andExpect(jsonPath("$[*].gender").value(containsInAnyOrder("Male", "Male")));
  }

  @Test
  public void should_return_company_when_perform_get_given_page_and_page_size() throws Exception {
    //given
    companyRepositoryInMongo.insert(new Company(null, "comm"));
    companyRepositoryInMongo.insert(new Company(null, "comm2"));
    companyRepositoryInMongo.insert(new Company(null, "comm33"));

    //when
    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE + "?page=1&pageSize=2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[*].companyName").value(containsInAnyOrder("comm", "comm2")));
  }

  @Test
  public void should_create_company_when_POST_given_company() throws Exception {
    String companyJsonString = "{\n" +
      "        \"companyName\": \"cannotFindCompany222\"\n" +
      "    }";

    mockMvc.perform(MockMvcRequestBuilders.post(COMPANIES_URL_BASE)
        .contentType(MediaType.APPLICATION_JSON)
        .content(companyJsonString))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").isString())
      .andExpect(jsonPath("$.companyName").value("cannotFindCompany222"));
  }

  @Test
  public void should_return_edited_company_when_put_given_updated_employee() throws Exception {
    Company company = new Company(null, "comm");
    companyRepositoryInMongo.insert(company);

    String updatedCompanyJson = "{\n" +
      "        \"companyName\": \"cannotFindCompany222\"\n" +
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
    Company company = new Company(null, "comm");
    companyRepositoryInMongo.insert(company);

    mockMvc.perform(MockMvcRequestBuilders.delete(COMPANIES_URL_BASE + "/" + company.getId()))
      .andExpect(status().isNoContent());

    mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(0)));
  }
}
