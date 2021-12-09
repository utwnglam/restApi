package com.afs.restapi.controller;

import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.mapper.EmployeeMapper;
import com.afs.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private final EmployeeService employeeService;
  private final EmployeeMapper employeeMapper;

  public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
    this.employeeService = employeeService;
    this.employeeMapper = employeeMapper;
  }

  @GetMapping
  public List<EmployeeResponse> getAllEmployees() {
    return employeeService.findAll().stream()
      .map(employeeMapper::toResponse)
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public EmployeeResponse getEmployeeById(@PathVariable String id) {
    return employeeMapper.toResponse(employeeService.findById(id));
  }

  @GetMapping(params = "gender")
  public List<EmployeeResponse> getEmployeesByGender(@RequestParam String gender) {
    return employeeService.findByGender(gender).stream()
      .map(employeeMapper::toResponse)
      .collect(Collectors.toList());
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<EmployeeResponse> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
    return employeeService.findByPage(page, pageSize).stream()
      .map(employeeMapper::toResponse)
      .collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
    return employeeMapper.toResponse(employeeService.create(employeeMapper.toEntity(employeeRequest)));
  }

  @PutMapping("/{id}")
  public EmployeeResponse editEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest) {
    return employeeMapper.toResponse(employeeService.edit(id, employeeMapper.toEntity(employeeRequest)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable String id) {
    employeeService.delete(id);
  }
}
