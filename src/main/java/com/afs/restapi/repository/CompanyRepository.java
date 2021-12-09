package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import com.afs.restapi.exception.NoCompanyFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
  private List<Company> companies = new ArrayList<>();

  public CompanyRepository() {
    companies.add(new Company("1", "comm"));
    companies.add(new Company("2", "comingCompany"));
    companies.add(new Company("3", "cannotFindCompany"));
  }

  public List<Company> findAll() {
    return companies;
  }

  public Company findById(String id) {
    return companies.stream()
      .filter(company -> company.getId().equals(id))
      .findFirst()
      .orElseThrow(NoCompanyFoundException::new);
  }

  public List<Company> findByPageNumber(Integer page, Integer pageSize) {
    int pageToBeSkipped = page - 1;
    return companies.stream()
      .skip((long) pageToBeSkipped * pageSize)
      .limit(pageSize)
      .collect(Collectors.toList());
  }

  public Company create(Company company) {
    int nextId = companies.stream()
      .mapToInt(item -> Integer.parseInt(item.getId()))
      .max()
      .orElse(0) + 1;

    company.setId(String.valueOf(nextId));
    companies.add(company);
    return company;
  }

  public Company edit(String id, Company updatedCompany) {
    Company company = findById(id);
    companies.remove(company);
    companies.add(updatedCompany);
    return updatedCompany;
  }

  public void delete(String id) {
    Company company = findById(id);
    companies.remove(company);
  }

  public void clearAll() {
    companies.clear();
  }
}
