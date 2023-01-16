package com.drone.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends RuntimeException {
  public DroneNotFoundException(String message) {
    super(message);
  }

  public DroneNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}