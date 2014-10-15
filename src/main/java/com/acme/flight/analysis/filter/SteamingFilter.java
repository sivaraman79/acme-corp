package com.acme.flight.analysis.filter;

public interface SteamingFilter<T> extends Filter<T> {
  boolean accepts(T t);
}
