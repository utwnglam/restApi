package com.afs.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeRepository employeeRepository;

  public EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @GetMapping("/{id}")
  public Employee getEmployeeById(@PathVariable Integer id) {
    return employeeRepository.findById(id);
  }

  @GetMapping(params = "gender")
  // more than one need {}
  public List<Employee> getEmployeesByGender(@RequestParam String gender) {
    return employeeRepository.findByGender(gender);
  }

  @PostMapping
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeRepository.create(employee);
  }

  @PutMapping("/{id}")
  public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
    Employee employee = employeeRepository.findById(id);
    if (updatedEmployee.getAge() != null) {
      employee.setAge(updatedEmployee.getAge());
    }
    if (updatedEmployee.getSalary() != null) {
      employee.setSalary(updatedEmployee.getSalary());
    }
    return employeeRepository.save(id, updatedEmployee);
  }

}
