package com.haiph.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haiph.common.dto.response.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException {

  @JsonProperty("responseCode")
  private final Response response;

  @JsonProperty("responseMessage")
  private final String message;

  public CommonException(Response response, String message) {
    super(message);
    this.response = response;
    this.message = message == null ? response.getResponseMessage() : message;
  }

  public CommonException(Response response) {
    super(response.getResponseMessage());
    this.response = response;
    this.message = response.getResponseMessage();
  }

  public CommonException(String responseCode, String message) {
    super(message);
    this.response = Response.fromCode(responseCode);
    this.message = message == null ? response.getResponseMessage() : message;
  }

  @Override
  public String toString() {
    return "{"
      + "\"responseCode\":" + "\"" + this.getResponse().getResponseCode() + "\"" + ","
      + "\"responseMessage\":" + "\"" + (this.getMessage() == null ? this.getResponse()
      .getResponseMessage() : this.getMessage()) + "\""
      + "}";
  }
}
