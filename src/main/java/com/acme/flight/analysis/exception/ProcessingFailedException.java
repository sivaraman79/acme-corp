package com.acme.flight.analysis.exception;

/**
 * Wrapper to enclose all different native exceptions so that all handlers can throw this exception
 * irrespective of undelying exceptions
 * 
 * @author thekalinga
 *
 */
public class ProcessingFailedException extends Exception {

  public ProcessingFailedException() {
  }

  public ProcessingFailedException(String message) {
    super(message);
  }

  public ProcessingFailedException(Throwable cause) {
    super(cause);
  }

  public ProcessingFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProcessingFailedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
