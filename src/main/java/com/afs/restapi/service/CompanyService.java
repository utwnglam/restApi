package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;
  private final EmployeeService employeeService;

  public CompanyService(CompanyRepository companyRepository, EmployeeService employeeService) {
    this.companyRepository = companyRepository;
    this.employeeService = employeeService;
  }

  public List<Company> findAll() {
    return companyRepository.findAll();
  }

  public Company findById(String id) {
    return companyRepository.findById(id);
  }

  public List<Company> findByPage(Integer page, Integer pageSize) {
    return companyRepository.findByPageNumber(page, pageSize);
  }

  public Company create(Company company) {
    return companyRepository.create(company);
  }

  public void delete(String id) {
    companyRepository.delete(id);
  }

  public Company edit(String id, Company updatedCompany) {
    Company company = companyRepository.findById(id);
    if (updatedCompany.getCompanyName() != null && !updatedCompany.getCompanyName().equals("")) {
      company.setCompanyName(updatedCompany.getCompanyName());
    }
    return companyRepository.edit(id, company);
  }

  public List<Employee> findEmployeesByCompanyId(String id) {
    return employeeService.findByCompanyId(id);
  }
}
