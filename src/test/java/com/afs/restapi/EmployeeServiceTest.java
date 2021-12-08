package com.afs.restapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
  @Mock
  EmployeeRepository mockEmployeeRepository;
  @InjectMocks
  EmployeeService employeeService;

  @Test
  public void should_return_all_employees_when_get_given_employees() {
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee(1, "Terence", 29, "Male", 66666));
    given(mockEmployeeRepository.findAll())
      .willReturn(employees);

    List<Employee> actual = employeeService.findAll();

    assertEquals(employees, actual);
  }

  @Test
  public void should_return_updated_employee_when_edit_given_updated_employee() {
    Employee employee = new Employee(1, "Terence", 29, "Male", 66666);
    Employee updatedEmployee = new Employee(1, "Jooo", 19, "Female", 18888);
    given(mockEmployeeRepository.findById(any()))
      .willReturn(employee);
    employee.setAge(updatedEmployee.getAge());
    employee.setSalary(updatedEmployee.getSalary());
    given(mockEmployeeRepository.save(any(),any(Employee.class)))
      .willReturn(employee);

    Employee actual = employeeService.edit(employee.getId(),updatedEmployee);
    assertEquals(employee, actual);
  }

  @Test
  public void should_return_employee_when_get_given_ID() {
    Employee employee = new Employee(1, "Terence", 29, "Male", 66666);
    given(mockEmployeeRepository.findById(any()))
      .willReturn(employee);
    Employee actual = employeeService.findById(employee.getId());
    assertEquals(employee, actual);
  }

  @Test
  public void should_return_employee_when_get_given_gender() {

  }
}
