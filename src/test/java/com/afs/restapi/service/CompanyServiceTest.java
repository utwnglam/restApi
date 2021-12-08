package com.afs.restapi.service;

import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
  @Mock
  CompanyRepository companyRepository;
  @InjectMocks
  CompanyService companyService;

  @Test
  public void should__when__given_() {

  }
}
