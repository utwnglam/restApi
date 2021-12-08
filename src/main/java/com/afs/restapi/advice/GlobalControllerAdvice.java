package com.afs.restapi.advice;

import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.exception.NoEmployeeFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseStatus(HttpStatus.NOT_FOUND)
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler({NoEmployeeFoundException.class, NoCompanyFoundException.class})
  public ErrorResponse handleNotFound(Exception exception) {
    return new ErrorResponse(404, "Entity Not Found.");
  }
}
