package com.acme.flight.analysis.exception;

/**
 * Wrapper to enclose all different native exceptions so that all handlers can throw this exception
 * irrespective of undelying exceptions
 * 
 * @author thekalinga
 *
 */
public class ProcessingFailedException extends Exception {

  private static final long serialVersionUID = 492720099292582236L;

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
