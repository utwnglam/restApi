package com.afs.restapi.entity;

import java.util.ArrayList;
import java.util.List;

public class Company {
  private Integer id;
  private String companyName;
  private List<Employee> employees;

  public Company(Integer id, String companyName) {
    this.id = id;
    this.companyName = companyName;
    this.employees = new ArrayList<>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
}
