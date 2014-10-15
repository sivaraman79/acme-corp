package com.acme.flight.analysis.filter;

import java.util.Collection;

/**
 * Filter the data based on the input info provided
 *
 * @author Ashok.Koyi
 *
 * @param <T> Input type to be filtered
 */
public interface Filter<T> {
  Collection<T> getSelectedArrivalInfos();
  Collection<T> getIgnoreArrivalInfos();
}
