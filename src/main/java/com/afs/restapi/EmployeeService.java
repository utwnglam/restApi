package com.afs.restapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee edit(Integer id, Employee updatedEmployee) {
    Employee employee = employeeRepository.findById(id);
    if (updatedEmployee.getAge() != null) {   //  !.equals(0)
      employee.setAge(updatedEmployee.getAge());
    }
    if (updatedEmployee.getSalary() != null) {  //  !.equals(0)
      employee.setSalary(updatedEmployee.getSalary());
    }
    return employeeRepository.save(id, employee);
  }

  public Employee findById(Integer id) {
    return employeeRepository.findById(id);
  }

  public List<Employee> findByGender(String gender) {
    return employeeRepository.findByGender(gender);
  }

  public List<Employee> findByPage(int page, int pageSize) {
    return employeeRepository.findByPageNumber(page,pageSize);
  }
}
