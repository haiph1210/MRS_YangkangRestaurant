package com.haiph.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final String msg;

  public ValidationException(String msg) {
    this.msg = msg;
  }
}
