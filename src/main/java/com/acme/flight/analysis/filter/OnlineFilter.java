package com.acme.flight.analysis.filter;

import com.acme.flight.analysis.writer.Writer;

public interface OnlineFilter<T> extends Filter<T> {
  void apply(T t, Writer<T> matchedEntryWriter, Writer<T> unmatchedEntryWriter);

  void postProcess(Writer<T> matchedEntryWriter, Writer<T> unmatchedEntryWriter);
}
