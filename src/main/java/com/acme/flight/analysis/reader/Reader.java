package com.acme.flight.analysis.reader;


public interface Reader<T> {
  T read();
  boolean hasMoreItems();
}
