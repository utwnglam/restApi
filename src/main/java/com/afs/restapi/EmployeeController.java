package com.afs.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return employeeService.findAll();
  }

  @GetMapping("/{id}")
  public Employee getEmployeeById(@PathVariable Integer id) {
    return employeeService.findById(id);
  }

  @GetMapping(params = "gender")
  public List<Employee> getEmployeesByGender(@RequestParam String gender) {
    return employeeService.findByGender(gender);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Employee> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
    return employeeService.findByPage(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeService.create(employee);
  }

  @PutMapping("/{id}")
  public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
    return employeeService.edit(id, updatedEmployee);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable Integer id) {
    employeeService.delete(id);
  }
}
