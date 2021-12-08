package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
  private CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public List<Company> findAll() {
    return companyRepository.findAll();
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
    return companyRepository.edit(id, company);
  }
}
