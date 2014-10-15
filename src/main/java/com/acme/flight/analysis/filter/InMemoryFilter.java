package com.acme.flight.analysis.filter;

/**
 * Implementations of this interface will filter the data based on the overall info. If you can
 * filter the data by seeing the current item at hand & from the metadata gained from the previous
 * data, consider using {@link SteamingFilter} instead as it is more memory efficient
 *
 * @author Ashok.Koyi
 *
 * @param <T> Input type to be filtered
 */
public interface InMemoryFilter<T> extends Filter<T> {
  void filter(T t) throws IllegalStateException;

  void postProcess() throws IllegalStateException;
}
