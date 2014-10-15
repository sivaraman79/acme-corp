package com.acme.flight.analysis.filter;


/**
 * Filters the data
 *
 * @author Ashok.Koyi
 *
 * @param <T> Input type to be filtered
 */
public interface Filter<T> {
  void apply(T t);
}
