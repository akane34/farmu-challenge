package com.farmu.webtoolsapi.controllers;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.ResultError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ResultError> noHandlerFoundException(
      HttpServletRequest req, NoHandlerFoundException ex) {
    ResultError resultError =
        new ResultError(
            String.format("Route %s not found", req.getRequestURI()),
                ErrorCode.SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultError);
  }

  /**
   * Handler for internal exceptions.
   * 
   * @param e the exception thrown during request processing.
   * @return {@link ResponseEntity} with 500 status code and description indicating an internal error.
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ResultError> handleUnknownException(Exception e) {
    log.error("Internal error", e);

    ResultError resultError =
        new ResultError(
            "Internal server error", ErrorCode.SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultError);
  }
}
