package com.afs.restapi.controller;

import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  private CompanyRepository companyRepository;

  public CompanyController(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @GetMapping
  public List<Company> getAllCompanies() {
    return companyRepository.findAll();
  }

  @GetMapping("/{id}")
  public Company getCompanyById(@PathVariable Integer id) {
    return companyRepository.findById(id);
  }

  @GetMapping("/{id}/employees")
  public List<Employee> getCompanyEmployeesList(@PathVariable Integer id) {
    return companyRepository.findEmployees(id);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Company> getCompaniesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
    return companyRepository.findByPageNumber(page, pageSize);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Company createCompany(@RequestBody Company company) {
    return companyRepository.create(company);
  }

  @PutMapping("/{id}")
  public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany) {
    Company company = companyRepository.findById(id);
    if (updatedCompany.getCompanyName() != null) {
      company.setCompanyName(updatedCompany.getCompanyName());
    }
    return companyRepository.edit(id, company);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable Integer id) {
    companyRepository.delete(id);
  }
}
