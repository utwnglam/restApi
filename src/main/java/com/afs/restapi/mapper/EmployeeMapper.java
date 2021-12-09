package com.afs.restapi.mapper;

import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
  public Employee toEntity(EmployeeRequest employeeRequest) {
    Employee employee = new Employee();

    BeanUtils.copyProperties(employeeRequest, employee);
    return employee;
  }

  public EmployeeResponse toResponse(Employee employee) {
    EmployeeResponse employeeResponse = new EmployeeResponse();

    BeanUtils.copyProperties(employee, employeeResponse);
    return employeeResponse;
  }
}
