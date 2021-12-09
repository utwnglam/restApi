package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
  private CompanyRepository companyRepository;
  private EmployeeRepository employeeRepository;

  public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
    this.companyRepository = companyRepository;
    this.employeeRepository = employeeRepository;
  }

  public List<Company> findAll() {
    List<Company> companies = companyRepository.findAll();
    companies.forEach((company) -> {
      company.setEmployees(findEmployeesByCompanyId(company.getId()));
    });
    return companies;
  }

  public Company findById(Integer id) {
    return companyRepository.findById(id);
  }

  public List<Company> findByPage(Integer page, Integer pageSize) {
    return companyRepository.findByPageNumber(page, pageSize);
  }

  public Company create(Company company) {
    return companyRepository.create(company);
  }

  public void delete(Integer id) {
    companyRepository.delete(id);
  }

  public Company edit(Integer id, Company updatedCompany) {
    Company company = companyRepository.findById(id);
    if (updatedCompany.getCompanyName() != null && !updatedCompany.getCompanyName().equals("")) {
      company.setCompanyName(updatedCompany.getCompanyName());
    }
    Company newCompany = companyRepository.edit(id, company);
    newCompany.setEmployees(findEmployeesByCompanyId(id));
    return newCompany;
  }

  public List<Employee> findEmployeesByCompanyId(Integer id) {
    return employeeRepository.findByCompanyId(id);
  }
}
