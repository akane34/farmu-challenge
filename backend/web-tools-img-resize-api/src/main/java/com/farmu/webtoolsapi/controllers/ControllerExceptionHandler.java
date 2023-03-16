package com.farmu.webtoolsapi.controllers;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.ResultError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  /**
   * Handler for not found routes.
   * 
   * @param req the incoming request.
   * @param ex the exception thrown when route is not found.
   * @return {@link ResponseEntity} with 404 status code and the route that was not found in the body.
   */
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
    LOGGER.error("Internal error", e);

    ResultError resultError =
        new ResultError(
            "Internal server error", ErrorCode.SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultError);
  }
}
