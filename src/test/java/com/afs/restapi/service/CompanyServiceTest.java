package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
  @Mock
  CompanyRepository mockCompanyRepository;
  @InjectMocks
  CompanyService companyService;

  @Test
  public void should_get_all_companies_when_GET_given_companies() {
    List<Company> companies = new ArrayList<>();
    companies.add(new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    ));
    given(mockCompanyRepository.findAll())
      .willReturn(companies);

    List<Company> actual = companyService.findAll();

    assertEquals(companies, actual);
  }

  @Test
  public void should_return_company_when_get_given_ID() {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    given(mockCompanyRepository.findById(1))
      .willReturn(company);

    Company actual = companyService.findById(company.getId());
    assertEquals(company, actual);
  }

  @Test
  public void should_return_company_when_get_given_page_and_page_size() {
    List<Company> companies = new ArrayList<>();
    companies.add(new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    ));
    companies.add(new Company(2, "comm2",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    ));

    given(mockCompanyRepository.findByPageNumber(1,2))
      .willReturn(companies);

    List<Company> actual = companyService.findByPage(1,2);
    assertEquals(companies, actual);
  }

  @Test
  public void should_return_updated_company_when_edit_given_updated_company() {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    Company updatedCompany = new Company(1, "comm222",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );

    given(mockCompanyRepository.findById(any()))
      .willReturn(company);
    company.setCompanyName(updatedCompany.getCompanyName());
    given(mockCompanyRepository.edit(any(),any(Company.class)))
      .willReturn(company);

    Company actual = companyService.edit(company.getId(), updatedCompany);
    assertEquals(company, actual);
  }

  @Test
  public void should_create_company_when_create_given_company() {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    given(mockCompanyRepository.create(company))
      .willReturn(company);

    Company actual = companyService.create(company);
    assertEquals(company, actual);
  }

  @Test
  public void should_return_null_list_when_delete_given_deleted_company() {
    Company company = new Company(1, "comm",
      Arrays.asList(new Employee(1, "Terence", 29, "Male", 66666),
        new Employee(2, "wh", 20, "Female", 10000))
    );
    given(mockCompanyRepository.create(company))
      .willReturn(company);
    given(mockCompanyRepository.findAll())
      .willReturn(Collections.emptyList());

    companyService.create(company);
    companyService.delete(company.getId());
    int actual = companyService.findAll().size();

    assertEquals(0, actual);
  }
}
