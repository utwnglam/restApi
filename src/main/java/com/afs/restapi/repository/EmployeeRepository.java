package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoEmployeeFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
  private List<Employee> employees = new ArrayList<>();

  public EmployeeRepository() {
    employees.add(new Employee(1, "Terence", 29, "Male", 66666, 1));
    employees.add(new Employee(2, "Terence", 28, "Male", 66666, 1));
    employees.add(new Employee(3, "Terence", 27, "Male", 66666, 1));
    employees.add(new Employee(4, "Joanne", 26, "Female", 66666, 2));
    employees.add(new Employee(5, "Joanne", 25, "Female", 18888, 2));
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

  public List<Employee> findByCompanyId(Integer id) {
    return employees.stream()
      .filter(employee -> employee.getCompanyId().equals(id))
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

  public List<Employee> findByPageNumber(Integer page, Integer pageSize) {
    return employees.stream()
      .skip((long) page * pageSize)
      .limit(pageSize)
      .collect(Collectors.toList());
  }

  public void delete(Integer id) {
    Employee employee = findById(id);
    employees.remove(employee);
  }

  public void clearAll() {
    employees.clear();
  }
}
