package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.repository.CompanyRepositoryInMongo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
  private final CompanyRepositoryInMongo companyRepositoryInMongo;
  private final EmployeeService employeeService;

  public CompanyService(CompanyRepositoryInMongo companyRepositoryInMongo, EmployeeService employeeService) {
    this.companyRepositoryInMongo = companyRepositoryInMongo;
    this.employeeService = employeeService;
  }

  public List<Company> findAll() {
    return companyRepositoryInMongo.findAll();
  }

  public Company findById(String id) {
    return companyRepositoryInMongo.findById(id)
      .orElseThrow(NoCompanyFoundException::new);
  }

  public List<Company> findByPage(Integer page, Integer pageSize) {
    return companyRepositoryInMongo.findAll(PageRequest.of(page-1, pageSize))
      .getContent();
  }

  public Company create(Company company) {
    return companyRepositoryInMongo.insert(company);
  }

  public void delete(String id) {
    companyRepositoryInMongo.delete(findById(id));
  }

  public Company edit(String id, Company updatedCompany) {
    Company company = findById(id);
    if (updatedCompany.getCompanyName() != null && !updatedCompany.getCompanyName().equals("")) {
      company.setCompanyName(updatedCompany.getCompanyName());
    }
    return companyRepositoryInMongo.save(company);
  }

  public List<Employee> findEmployeesByCompanyId(String id) {
    return employeeService.findByCompanyId(id);
  }
}
