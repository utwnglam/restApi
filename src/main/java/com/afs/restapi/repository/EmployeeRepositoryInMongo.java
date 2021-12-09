package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryInMongo extends MongoRepository<Employee, String> {

}
