package com.alexbegt.ghostkitchen.web.util;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class GenericResponse {

  private String message;
  private String error;

  public GenericResponse(final String message) {
    this.message = message;
  }

  public GenericResponse(final String message, final String error) {
    this.message = message;
    this.error = error;
  }

  public GenericResponse(List<ObjectError> allErrors, String error) {
    this.error = error;

    String temp = allErrors.stream().map(e -> {
      if (e instanceof FieldError) {
        return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage()
          + "\"}";
      }
      else {
        return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
      }
    }).collect(Collectors.joining(","));

    this.message = "[" + temp + "]";
  }

  /**
   * Gets the message to use for the Generic Response.
   *
   * @return the message
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Sets the message to use for the Generic Response.
   *
   * @param message the new message
   */
  public void setMessage(final String message) {
    this.message = message;
  }

  /**
   * Gets the error message to use for the Generic Response.
   *
   * @return the error message
   */
  public String getError() {
    return this.error;
  }

  /**
   * Sets the error message to use for the Generic Response.
   *
   * @param error the new error
   */
  public void setError(final String error) {
    this.error = error;
  }
}
