package com.acme.flight.analysis.handler;

import com.acme.flight.analysis.exception.ProcessingFailedException;

public interface DataHandler<T> {
  void handle(T t);

  void postProcess() throws ProcessingFailedException;
}
