package com.acme.flight.analysis.filter;

import com.acme.flight.analysis.writer.Writer;

/**
 * Used to filter data on the fly without loading the memory than necessary. Some usecases require
 * the complete data to be stored in memory to filter the data appropriately. For those scenarios
 * use {@link CollectingFilter}.
 * 
 * <p>
 * Since this is an online filter, he will try to flush the filtered results as soon as he can. So
 * expect to use overloaded apply method.
 * 
 * @author thekalinga
 *
 * @param <T> Input type to be filtered
 */
public interface OnlineFilter<T> extends Filter<T> {
  /**
   * Applies the filter
   * 
   * @param t Input type to be filtered
   * @param matchedEntryWriter Writer to be used for persisting matched data
   * @param unmatchedEntryWriter Writer to be used for persisting unmatched data
   */
  void apply(T t, Writer<T> matchedEntryWriter, Writer<T> unmatchedEntryWriter);

  /**
   * Gives a chance for the filter to any post processing steps such as flushing the final bytes
   * 
   * @param matchedEntryWriter Writer to be used for persisting matched data
   * @param unmatchedEntryWriter Writer to be used for persisting unmatched data
   */
  void postProcess(Writer<T> matchedEntryWriter, Writer<T> unmatchedEntryWriter);
}
