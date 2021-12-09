package com.afs.restapi.entity;

public class Company {
  private String id;
  private String companyName;

  public Company(String id, String companyName) {
    this.id = id;
    this.companyName = companyName;
  }

  public Company() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

}
