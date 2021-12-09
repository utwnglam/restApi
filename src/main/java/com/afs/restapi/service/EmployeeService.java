package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee findById(String id) {
    return employeeRepository.findById(id);
  }

  public List<Employee> findByGender(String gender) {
    return employeeRepository.findByGender(gender);
  }

  public List<Employee> findByPage(Integer page, Integer pageSize) {
    return employeeRepository.findByPageNumber(page, pageSize);
  }

  public Employee create(Employee employee) {
    return employeeRepository.create(employee);
  }

  public Employee edit(String id, Employee updatedEmployee) {
    Employee employee = employeeRepository.findById(id);
    if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0)) {
      employee.setAge(updatedEmployee.getAge());
    }
    if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0)) {
      employee.setSalary(updatedEmployee.getSalary());
    }
    return employeeRepository.save(id, employee);
  }

  public void delete(String id) {
    employeeRepository.delete(id);
  }

  public List<Employee> findByCompanyId(String id) {
    return employeeRepository.findByCompanyId(id);
  }
}
