package com.farmu.webtoolsapi.commons.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ResultError{

  @JsonProperty("message")
  private String message;
  @JsonProperty("code")
  private ErrorCode code;

  public ResultError() { }

  public ResultError(String message, ErrorCode code) {
    this.message = message;
    this.code = code;
  }
}
