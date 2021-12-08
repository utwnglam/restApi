package com.afs.restapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public void edit(Integer id, Employee updatedEmployee) {
  }
}
