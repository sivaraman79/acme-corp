package com.acme.flight.analysis.reader;


/**
 * Source agnostic interface for reading stream of data
 * 
 * @author thekalinga
 *
 * @param <T> Input type to be read
 */
public interface Reader<T> {
  /**
   * Reads input object
   * 
   * @return Input data read
   */
  T read();

  /**
   * Help you decide whether to call read further or not
   * 
   * @return true if more elements to be read are present in the input
   */
  boolean hasMoreItems();
}
