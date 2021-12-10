package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.exception.NoEmployeeFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryInMongo;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
  @Mock
  CompanyRepositoryInMongo mockCompanyRepositoryInMongo;

  @InjectMocks
  CompanyService companyService;

  @Test
  public void should_get_all_companies_when_GET_given_companies() {
    List<Company> companies = new ArrayList<>();
    companies.add(new Company(null, "comm"));

    given(mockCompanyRepositoryInMongo.findAll())
      .willReturn(companies);

    List<Company> actual = companyService.findAll();

    assertEquals(companies.get(0).getCompanyName(), actual.get(0).getCompanyName());
  }

  @Test
  public void should_return_company_when_get_given_ID() {
    Company company = new Company("1", "comm");
    given(mockCompanyRepositoryInMongo.findById("1"))
      .willReturn(java.util.Optional.of(company));

    Company actual = companyService.findById(company.getId());

    assertEquals(company.getCompanyName(), actual.getCompanyName());
  }

  @Test
  public void should_return_company_when_get_given_page_and_page_size() {
    List<Company> companies = new ArrayList<>();
    companies.add(new Company("1", "comm"));
    companies.add(new Company("2", "company2"));

    PageImpl<Company> pageToBeReturned = new PageImpl<>(companies, PageRequest.of(1, 2), 1);
    given(mockCompanyRepositoryInMongo.findAll(any(PageRequest.class)))
      .willReturn(pageToBeReturned);

    List<Company> actual = companyService.findByPage(1,2);

    assertEquals(companies.get(0).getCompanyName(), actual.get(0).getCompanyName());
    assertEquals(companies.get(1).getCompanyName(), actual.get(1).getCompanyName());
  }

  @Test
  public void should_throw_exception_when_get_given_not_exist_company() {
    Company company = new Company("1", "comm");
    given(mockCompanyRepositoryInMongo.findById("1221"))
      .willThrow(NoCompanyFoundException.class);

    assertThrows(NoCompanyFoundException.class, () -> companyService.findById(company.getId()));
  }

  @Test
  public void should_return_updated_company_when_edit_given_updated_company() {
    Company company = new Company("1", "comm");
    Company updatedCompany = new Company("1", "comm222");

    given(mockCompanyRepositoryInMongo.findById(any()))
      .willReturn(java.util.Optional.of(company));
    company.setCompanyName(updatedCompany.getCompanyName());
    given(mockCompanyRepositoryInMongo.save(any(Company.class)))
      .willReturn(company);

    Company actual = companyService.edit(company.getId(), updatedCompany);
    assertEquals(company.getCompanyName(), actual.getCompanyName());
  }

  @Test
  public void should_create_company_when_create_given_company() {
    Company company = new Company("1", "comm");
    given(mockCompanyRepositoryInMongo.insert(company))
      .willReturn(company);

    Company actual = companyService.create(company);
    assertEquals(company, actual);
  }

  @Test
  public void should_return_null_list_when_delete_given_deleted_company() {
    Company company = new Company("1", "comm");
    given(mockCompanyRepositoryInMongo.insert(company))
      .willReturn(company);
    given(mockCompanyRepositoryInMongo.findAll())
      .willReturn(Collections.emptyList());
    given(mockCompanyRepositoryInMongo.findById(company.getId()))
      .willReturn(java.util.Optional.of(company));
    doNothing().when(mockCompanyRepositoryInMongo).delete(company);

    companyService.create(company);
    companyService.delete(company.getId());
    int actual = companyService.findAll().size();

    assertEquals(0, actual);
  }
}
