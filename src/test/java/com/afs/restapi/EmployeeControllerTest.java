package com.afs.restapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
  EmployeeRepository employeeRepository;

  @BeforeEach
  void cleanRepository() {
    employeeRepository.clearAll();
  }

  @Test
  public void should_get_all_employees_when_GET_given_employee() throws Exception {
    Employee employee = new Employee(1, "Terence", 29, "Male", 66666);
    employeeRepository.create(employee);

    mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEES_URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[0].name").value("Terence"))
      .andExpect(jsonPath("$[0].age").value(29))
      .andExpect(jsonPath("$[0].gender").value("Male"))
      .andExpect(jsonPath("$[0].salary").value(6666));
  }

  @Test
  void should_return_employee_when_perform_get_given_employee_id() throws Exception {
    //given
    Employee employee = new Employee(1,"john",20,"male",1000);
    employeeRepository.create(employee);

    mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").isNumber())
      .andExpect(jsonPath("$.name").value("john"))
      .andExpect(jsonPath("$.age").value(20))
      .andExpect(jsonPath("$.gender").value("male"))
      .andExpect(jsonPath("$.salary").value(1000));
  }

  @Test
  void should_get_employee_when_perform_get_given_gender() throws Exception {
    //given
    Employee employee = new Employee(1,"john",20,"male",1000);
    employeeRepository.create(employee);
    mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=male"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(1)))
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[0].name").value("john"))
      .andExpect(jsonPath("$[0].age").value(20))
      .andExpect(jsonPath("$[0].gender").value("male"))
      .andExpect(jsonPath("$[0].salary").value(1000));
  }

  @Test
  void should_return_employees_when_perform_get_given_page_and_page_size() throws Exception {
    //given
    employeeRepository.create( new Employee(1,"john",20,"male",1000));
    employeeRepository.create( new Employee(1,"john",20,"male",1000));
    employeeRepository.create( new Employee(1,"john",20,"male",1000));
    //when
    mockMvc.perform(MockMvcRequestBuilders.get("/employees?page=1&pageSize=2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(3)))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[2].id").value(3));
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
      .andExpect(jsonPath("$.gender").value("Female"))
      .andExpect(jsonPath("$.salary").value(18888));
  }

  @Test
  void should_return_edited_employee_when_perform_put_given_updated_employee() throws Exception {
    Employee employee = new Employee(1,"john",20,"male",1000);
    employeeRepository.create(employee);

    String updatedEmployee = "{\"id\": 1,\n" +
      "                \"name\": \"john\",\n" +
      "                \"age\": 18,\n" +
      "                \"gender\": \"male\",\n" +
      "                \"salary\": 1100}";

    mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedEmployee))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.age").value(18))
      .andExpect(jsonPath("$.salary").value(1100));
  }

  @Test
  void should_delete_when_perform_delete_given_employee_id() throws Exception {
    //given
    Employee employee = new Employee(1,"john",20,"male",1000);
    employeeRepository.create(employee);

    mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",employee.getId()))
      .andExpect(status().isNoContent());

    mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$",hasSize(0)));
  }
}
