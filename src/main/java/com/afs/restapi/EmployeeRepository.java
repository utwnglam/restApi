package com.afs.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
  private List<Employee> employees = new ArrayList<>();

  public EmployeeRepository() {
    employees.add(new Employee(1, "who", 20, "Female", 10000));
  }

  public List<Employee> findAll() {
    return employees;
  }

