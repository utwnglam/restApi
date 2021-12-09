package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepositoryInMongo extends MongoRepository<Employee, String> {
  List<Employee> findByGender(String gender);
  List<Employee> findByCompanyId(String companyId);
}
