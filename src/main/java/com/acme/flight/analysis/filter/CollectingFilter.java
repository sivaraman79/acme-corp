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
 * @param <T> Input type to be filtered
 */
public interface CollectingFilter<T> extends Filter<T> {
  /**
   * Gives an opportunity to proces the collected entries till the end. Call this method at the end
   * of input stream
   */
  void processCollectedEntries();

  /**
   * @return collection of matched entries
   */
  Collection<T> matchedEntries();

  /**
   * @return collection of unmatched entries
   */
  Collection<T> unmatchedEntries();
}
