package com.acme.flight.analysis.writer;

import java.io.IOException;

public interface Writer<T> {
  void write(T t) throws IOException;

  void close() throws IOException;

  /**
   * Does not do anything
   * 
   * @author thekalinga
   *
   * @param <T> input type
   */
  public static class NoOpWriter<T> implements Writer<T> {
    @Override
    public void write(T t) throws IOException {}

    @Override
    public void close() throws IOException {}
    
    @Override
    public String toString() {
      return "{No-Op writer}";
    }
  }
}
