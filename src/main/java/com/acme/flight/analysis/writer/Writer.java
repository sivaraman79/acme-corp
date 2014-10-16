package com.acme.flight.analysis.writer;

import java.io.IOException;

/**
 * Source agnostic interface for writing stream of data
 * 
 * @author thekalinga
 *
 * @param <T> Type to write
 */
public interface Writer<T> {
  /**
   * Write data to the underlying stream
   * 
   * @param t Data to write
   * @throws IOException
   */
  void write(T t) throws IOException;

  /**
   * Gives an opportunity to close any underlying resources the writer might have opened
   * 
   * @throws IOException
   */
  void close() throws IOException;

  /**
   * Writer that does nothing. Will be useful to avoid null checks in the code
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
