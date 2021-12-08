package com.afs.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeRepository employeeRepository;
  private EmployeeService employeeService;

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
  public List<Employee> getEmployeesByGender(@RequestParam String gender) {
    return employeeRepository.findByGender(gender);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Employee> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
    return employeeRepository.findByPageNumber(page, pageSize);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeRepository.create(employee);
  }

  @PutMapping("/{id}")
  public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
    return employeeService.edit(id, updatedEmployee);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Integer id) {
    employeeRepository.delete(id);
  }
}
