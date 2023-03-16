package com.farmu.webtoolsapi.commons.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
  @JsonProperty("result")
  private T result;
  @JsonProperty("errors")
  private List<ResultError> errors;

  public Result() {

  }

  public Result(T result) {
    this.result = result;
  }

  @JsonProperty("isSuccess")
  public boolean isSuccess() {
    return getErrors().isEmpty();
  }

  public T getResult() {
    return result;
  }

  public Result setResult(T result) {
    this.result = result;
    return this;
  }

  public List<ResultError> getErrors() {
    if (errors == null)
      errors = new ArrayList<>();
    return errors;
  }

  public Result setErrors(List<ResultError> errors) {
    if (errors instanceof ArrayList){
      this.errors = errors;
    }
    else {
      this.errors = new ArrayList<>();
      if (errors != null)
        this.errors.addAll(errors);
    }

    return this;
  }

  public Result addError(ResultError error) {
    getErrors().add(error);
    return this;
  }

  public Result addError(String message, ErrorCode code){
    if (Strings.isNotEmpty(message)) {
      addError(new ResultError(message, code));
    }
    return this;
  }
}
