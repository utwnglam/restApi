package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoEmployeeFoundException;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
  private final EmployeeRepositoryInMongo employeeRepositoryInMongo;

  public EmployeeService(EmployeeRepositoryInMongo employeeRepositoryInMongo) {
    this.employeeRepositoryInMongo = employeeRepositoryInMongo;
  }

  public List<Employee> findAll() {
    return employeeRepositoryInMongo.findAll();
  }

  public Employee findById(String id) {
    return employeeRepositoryInMongo.findById(id)
      .orElseThrow(NoEmployeeFoundException::new);
  }

  public List<Employee> findByGender(String gender) {
    return employeeRepositoryInMongo.findByGender(gender);
//    return employeeRepository.findByGender(gender);
  }

  public List<Employee> findByPage(Integer page, Integer pageSize) {
    return employeeRepositoryInMongo.findAll(PageRequest.of(page-1, pageSize))
      .getContent();
//    return employeeRepository.findByPageNumber(page, pageSize);
  }

  public Employee create(Employee employee) {
    return employeeRepositoryInMongo.insert(employee);
  }

  public Employee edit(String id, Employee updatedEmployee) {
    Employee employee = findById(id);
    if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0)) {
      employee.setAge(updatedEmployee.getAge());
    }
    if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0)) {
      employee.setSalary(updatedEmployee.getSalary());
    }
    return employeeRepositoryInMongo.save(employee);
//    return employeeRepository.save(id, employee);
  }

  public void delete(String id) {
    employeeRepositoryInMongo.delete(findById(id));
//    employeeRepository.delete(id);
  }

  public List<Employee> findByCompanyId(String companyId) {
    return employeeRepositoryInMongo.findByCompanyId(companyId);
//    return employeeRepository.findByCompanyId(id);
  }
}
