package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
  @Mock
  EmployeeRepositoryInMongo mockEmployeeRepositoryInMongo;

  @InjectMocks
  EmployeeService employeeService;

  @Test
  public void should_return_all_employees_when_get_given_employees() {
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee("1", "Terence", 29, "Male", 66666, "1"));
    given(mockEmployeeRepositoryInMongo.findAll())
      .willReturn(employees);

    List<Employee> actual = employeeService.findAll();

    assertEquals(employees.get(0).getName(), actual.get(0).getName());
    assertEquals(employees.get(0).getAge(), actual.get(0).getAge());
    assertEquals(employees.get(0).getGender(), actual.get(0).getGender());
  }

  @Test
  public void should_return_updated_employee_when_edit_given_updated_employee() {
    Employee employee = new Employee("1", "Terence", 29, "Male", 66666, "1");
    Employee updatedEmployee = new Employee("1", "Jooo", 19, "Female", 18888, "1");

    given(mockEmployeeRepositoryInMongo.findById(any()))
      .willReturn(java.util.Optional.of(employee));
    employee.setAge(updatedEmployee.getAge());
    employee.setSalary(updatedEmployee.getSalary());
    given(mockEmployeeRepositoryInMongo.save(any(Employee.class)))
      .willReturn(employee);

    Employee actual = employeeService.edit(employee.getId(), updatedEmployee);

    assertEquals(employee.getAge(), actual.getAge());
    assertEquals(employee.getSalary(), actual.getSalary());
  }

  @Test
  public void should_return_employee_when_get_given_ID() {
    Employee employee = new Employee("1", "Terence", 29, "Male", 66666, "1");
    given(mockEmployeeRepositoryInMongo.findById("1"))
      .willReturn(java.util.Optional.of(employee));

    Employee actual = employeeService.findById(employee.getId());

    assertEquals(employee.getName(), actual.getName());
    assertEquals(employee.getAge(), actual.getAge());
    assertEquals(employee.getGender(), actual.getGender());
  }

  @Test
  public void should_return_employee_when_get_given_gender() {
    Employee employee1 = new Employee("1", "Terence", 29, "Male", 66666, "1");
    Employee employee2 = new Employee("1", "Kai", 19, "Male", 18888, "1");

    List<Employee> employees = Arrays.asList(employee1, employee2);

    given(mockEmployeeRepositoryInMongo.findByGender("Male"))
      .willReturn(employees);

    List<Employee> actual = employeeService.findByGender("Male");

    assertEquals(employees.get(0).getName(), actual.get(0).getName());
    assertEquals(employees.get(0).getAge(), actual.get(0).getAge());
    assertEquals(employees.get(0).getGender(), actual.get(0).getGender());
    assertEquals(employees.get(1).getName(), actual.get(1).getName());
    assertEquals(employees.get(1).getAge(), actual.get(1).getAge());
    assertEquals(employees.get(1).getGender(), actual.get(1).getGender());
  }

  @Test
  public void should_return_employees_when_get_given_companyId() {
    Employee employee1 = new Employee("1", "Terence", 29, "Male", 66666, "1");
    Employee employee2 = new Employee("1", "Kai", 19, "Male", 18888, "1");
    List<Employee> employees = Arrays.asList(employee1, employee2);

    given(mockEmployeeRepositoryInMongo.findByCompanyId("1"))
      .willReturn(employees);

    List<Employee> actual = employeeService.findByCompanyId("1");

    assertEquals(employees.get(0).getName(), actual.get(0).getName());
    assertEquals(employees.get(0).getAge(), actual.get(0).getAge());
    assertEquals(employees.get(0).getSalary(), actual.get(0).getSalary());
    assertEquals(employees.get(1).getName(), actual.get(1).getName());
    assertEquals(employees.get(1).getAge(), actual.get(1).getAge());
    assertEquals(employees.get(1).getSalary(), actual.get(1).getSalary());
  }

  @Test
  public void should_return_employees_when_get_given_page_and_page_size() {
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee("1", "Terence", 29, "Male", 66666, "1"));
    employees.add(new Employee("2", "Kai", 19, "Male", 18888, "1"));
    employees.add(new Employee("3", "Kai", 19, "Male", 18888, "1"));

    PageImpl<Employee> pageToBeReturned = new PageImpl<>(employees, PageRequest.of(1, 2), 1);

    given(mockEmployeeRepositoryInMongo.findAll(any(PageRequest.class)))
      .willReturn(pageToBeReturned);

    List<Employee> actual = employeeService.findByPage(1,2);

    assertEquals(employees.get(0).getName(), actual.get(0).getName());
    assertEquals(employees.get(0).getAge(), actual.get(0).getAge());
    assertEquals(employees.get(0).getGender(), actual.get(0).getGender());
    assertEquals(employees.get(1).getName(), actual.get(1).getName());
    assertEquals(employees.get(1).getAge(), actual.get(1).getAge());
    assertEquals(employees.get(1).getGender(), actual.get(1).getGender());
  }

  @Test
  public void should_return_new_employee_when_post_given_new_employee() {
    Employee employee = new Employee("1", "Terence", 29, "Male", 66666, "1");

    given(mockEmployeeRepositoryInMongo.insert(employee))
      .willReturn(employee);

    Employee actual = employeeService.create(employee);

    assertEquals(employee.getName(), actual.getName());
    assertEquals(employee.getAge(), actual.getAge());
    assertEquals(employee.getGender(), actual.getGender());
  }

  @Test
  public void should_return_null_list_when_delete_given_deleted_employee() {
    Employee employee = new Employee("1", "Terence", 29, "Male", 66666, "1");
    given(mockEmployeeRepositoryInMongo.insert(employee))
      .willReturn(employee);
    given(mockEmployeeRepositoryInMongo.findAll())
      .willReturn(Collections.emptyList());
    given(mockEmployeeRepositoryInMongo.findById(employee.getId()))
      .willReturn(java.util.Optional.of(employee));
    doNothing().when(mockEmployeeRepositoryInMongo).delete(employee);

    employeeService.create(employee);
    employeeService.delete(employee.getId());
    int actual = employeeService.findAll().size();

    assertEquals(0, actual);
  }
}
