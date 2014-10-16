package com.acme.flight.analysis.handler;

import com.acme.flight.analysis.exception.ProcessingFailedException;

/**
 * Responsible for handling streaming data passed to it
 * 
 * @author thekalinga
 *
 * @param <T> Input type to be handled
 */
public interface DataHandler<T> {
  /**
   * Handles the data form stream. Usually
   * 
   * @param t Input type to be handled
   */
  void handle(T t);

  /**
   * Gives the underlying implementation to do final chance to do post processing
   * 
   * @throws ProcessingFailedException when any unexpected recoverable situation occurs
   */
  void postProcess() throws ProcessingFailedException;
}
