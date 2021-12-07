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

  public Employee findById(Integer id) {
    return employees.stream()
      .filter(employee -> employee.getId().equals(id))
      .findFirst()
      .orElseThrow(NoEmployeeFoundException::new);
  }

  public List<Employee> findByGender(String gender) {
    return employees.stream()
      .filter(employee -> employee.getGender().equals(gender))
      .collect(Collectors.toList());
  }

  public Employee create(Employee employee) {
    int nextId = employees.stream()
      .mapToInt(Employee::getId)
      .max()
      .orElse(0) + 1;

    employee.setId(nextId);
    employees.add(employee);
    return employee;
  }

  public Employee save(Integer id, Employee updatedEmployee) {
    Employee employee = findById(id);
    employees.remove(employee);
    employees.add(updatedEmployee);
    return updatedEmployee;
  }
}
