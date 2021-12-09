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
  private EmployeeService employeeService;

  public CompanyService(CompanyRepository companyRepository, EmployeeService employeeService) {
    this.companyRepository = companyRepository;
    this.employeeService = employeeService;
  }

  public List<Company> findAll() {
    List<Company> companies = companyRepository.findAll();
    companies.forEach(company -> company.setEmployees(findEmployeesByCompanyId(company.getId())));
    return companies;
  }

  public Company findById(Integer id) {
    Company company = companyRepository.findById(id);
    company.setEmployees(findEmployeesByCompanyId(id));
    return company;
  }

  public List<Company> findByPage(Integer page, Integer pageSize) {
    List<Company> companies = companyRepository.findByPageNumber(page, pageSize);
    companies.forEach(company -> company.setEmployees(findEmployeesByCompanyId(company.getId())));
    return companies;
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
    return employeeService.findByCompanyId(id);
  }
}
