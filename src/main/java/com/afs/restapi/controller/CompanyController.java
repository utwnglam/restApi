package com.afs.restapi.controller;

import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  private CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping
  public List<Company> getAllCompanies() {
    return companyService.findAll();
  }

  @GetMapping("/{id}")
  public Company getCompanyById(@PathVariable Integer id) {
    return companyService.findById(id);
  }

//  @GetMapping("/{id}/employees")
//  public List<Employee> getCompanyEmployeesList(@PathVariable Integer id) {
//    return companyService.findEmployees(id);
//  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Company> getCompaniesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
    return companyService.findByPage(page, pageSize);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Company createCompany(@RequestBody Company company) {
    return companyService.create(company);
  }

  @PutMapping("/{id}")
  public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany) {
    return companyService.edit(id, updatedCompany);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable Integer id) {
    companyService.delete(id);
  }
}
