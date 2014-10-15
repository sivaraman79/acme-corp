package com.acme.flight.analysis.filter;

import java.util.Collection;

/**
 * <p>
 * Filter that collects the steamed items and returns the collected items.
 * 
 * <p>
 * Use online filter ${@link OnlineFilter} if you don't want to keep the data in memory
 * 
 * @author thekalinga
 *
 * @param <T> item type to filter
 */
public interface CollectingFilter<T> extends Filter<T> {
  void processCollectedEntries();

  Collection<T> matchedEntries();

  Collection<T> unmatchedEntries();
}
