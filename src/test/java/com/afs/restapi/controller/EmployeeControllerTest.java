package com.afs.restapi.controller;

import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
  public static final String EMPLOYEES_URL_BASE = "/employees";
  @Autowired
  MockMvc mockMvc;
  @Autowired
  EmployeeRepositoryInMongo employeeRepositoryInMongo;

  @BeforeEach
//  @AfterEach
  void cleanRepository() {
    employeeRepositoryInMongo.deleteAll();
  }

  @Test
  public void should_get_all_employees_when_GET_given_employee() throws Exception {
    Employee employee = new Employee("1", "Terence", 29, "Male", 66666, "1");
    employeeRepositoryInMongo.insert(employee);
//    employeeRepository.create(employee);

    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id").isString())
      .andExpect(jsonPath("$[0].name").value("Terence"))
      .andExpect(jsonPath("$[0].age").value(29))
      .andExpect(jsonPath("$[0].gender").value("Male"));
  }

  @Test
  void should_return_employee_when_perform_get_given_employee_id() throws Exception {
    //given
    Employee employee = new Employee(null, "john",20,"male",1000, "1");
    employeeRepositoryInMongo.insert(employee);

    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE + "/" + employee.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").value("john"))
      .andExpect(jsonPath("$.age").value(20))
      .andExpect(jsonPath("$.gender").value("male"));
  }

  @Test
  void should_get_employee_when_perform_get_given_gender() throws Exception {
    //given
    Employee employee1 = new Employee(null,"john",20,"Male",1000, "1");
    Employee employee2 = new Employee(null, "Kai", 19, "Male", 18888, "1");
    employeeRepositoryInMongo.insert(employee1);
    employeeRepositoryInMongo.insert(employee2);

    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE + "?gender=Male"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(2)))
      .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("john", "Kai")))
      .andExpect(jsonPath("$[*].age").value(containsInAnyOrder(19, 20)))
      .andExpect(jsonPath("$[*].gender").value(containsInAnyOrder("Male", "Male")));
  }

  @Test
  void should_return_employees_when_perform_get_given_page_and_page_size() throws Exception {
    //given
    employeeRepositoryInMongo.insert(new Employee(null,"john",20,"Male",1000, "1"));
    employeeRepositoryInMongo.insert(new Employee(null, "Kai", 19, "Male", 18888, "1"));
    employeeRepositoryInMongo.insert(new Employee(null, "Terence", 29, "Male", 66666, "1"));

    //when
    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE + "?page=1&pageSize=2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("john", "Kai")))
      .andExpect(jsonPath("$[*].age").value(containsInAnyOrder(19, 20)))
      .andExpect(jsonPath("$[*].gender").value(containsInAnyOrder("Male", "Male")));
  }

  @Test
  public void should_create_employee_when_POST_given_employee() throws Exception {
    String employeeJsonString = "{\n" +
      "        \"name\": \"whoTest\",\n" +
      "        \"age\": 20,\n" +
      "        \"gender\": \"Female\",\n" +
      "        \"salary\": 18888\n" +
      "}";

    mockMvc.perform(MockMvcRequestBuilders.post(EMPLOYEES_URL_BASE)
          .contentType(MediaType.APPLICATION_JSON)
          .content(employeeJsonString))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.name").value("whoTest"))
      .andExpect(jsonPath("$.age").value(20))
      .andExpect(jsonPath("$.gender").value("Female"));
  }

  @Test
  void should_return_edited_employee_when_perform_put_given_updated_employee() throws Exception {
    Employee employee = new Employee(null,"john",20,"male",1000, "1");
    employeeRepositoryInMongo.insert(employee);

    String updatedEmployee = "{\"id\": 1,\n" +
      "                \"name\": \"john\",\n" +
      "                \"age\": 18,\n" +
      "                \"gender\": \"male\",\n" +
      "                \"salary\": 1100}";

    mockMvc.perform(MockMvcRequestBuilders.put(EMPLOYEES_URL_BASE + "/" + employee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedEmployee))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.age").value(18));
  }

  @Test
  void should_delete_when_perform_delete_given_employee_id() throws Exception {
    //given
    Employee employee = new Employee(null,"john",20,"male",1000, "1");
    employeeRepositoryInMongo.insert(employee);

    mockMvc.perform(MockMvcRequestBuilders.delete(EMPLOYEES_URL_BASE + "/" + employee.getId()))
      .andExpect(status().isNoContent());

    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(0)));
  }
}
