package com.acme.flight.analysis.handler;

import java.io.IOException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.exception.ProcessingFailedException;
import com.acme.flight.analysis.filter.CollectingFilter;
import com.acme.flight.analysis.writer.Writer;
import com.acme.flight.analysis.writer.Writer.NoOpWriter;

public class WritingDataHandlerWithCollectingFilter<T> implements DataHandler<T> {

  private static final Logger LOGGER = LogManager.getLogger();

  private CollectingFilter<T> collectingFilter;
  private Writer<T> matchedEntryWriter;
  private Writer<T> unmatchedEntryWriter;

  private WritingDataHandlerWithCollectingFilter(CollectingFilter<T> collectingFilter,
      Writer<T> matchedEntryWriter, Writer<T> unmatchedEntryWriter) {
    this.collectingFilter = collectingFilter;
    this.matchedEntryWriter = matchedEntryWriter;
    this.unmatchedEntryWriter = unmatchedEntryWriter;
  }

  @Override
  public void handle(T t) {
    collectingFilter.apply(t);
  }

  @Override
  public void postProcess() throws ProcessingFailedException {
    try {
      collectingFilter.processCollectedEntries();
      LOGGER.info("Saving matched entries using {} filtered by {}..", matchedEntryWriter, collectingFilter);
      persist(collectingFilter.matchedEntries(), matchedEntryWriter);
      LOGGER.info("Matched entry save complete.");
      LOGGER.info("Saving unmatched entries using {} filtered by {}..", unmatchedEntryWriter, collectingFilter);
      persist(collectingFilter.unmatchedEntries(), unmatchedEntryWriter);
      LOGGER.info("Unmatched entry save complete.");
    } catch (IOException e) {
      LOGGER.error("Unexpected IO exception occurred while saving the data. Reason: ", e);
      throw new ProcessingFailedException(e);
    }
  }

  private void persist(Collection<T> entries, Writer<T> writer) throws ProcessingFailedException, IOException {
    for (T t : entries) {
      try {
        writer.write(t);
      } catch (IOException e) {
        LOGGER.error("An IO exception occurred while writing the content. Reason: ", e);
        throw new ProcessingFailedException(e);
      }
    }
    writer.close();
  }

  public static class WritingDataHandlerWithCollectingFilterBuilder<T> {

    private Writer<T> matchedEntryWriter;
    private Writer<T> unmatchedEntryWriter;
    private CollectingFilter<T> collectingFilter;

    public WritingDataHandlerWithCollectingFilterBuilder(CollectingFilter<T> collectingFilter) {
      this.collectingFilter = collectingFilter;
    }

    public WritingDataHandlerWithCollectingFilterBuilder<T> matchedEntryWriter(Writer<T> matchedEntryWriter) {
      this.matchedEntryWriter = matchedEntryWriter;
      return this;
    }

    public WritingDataHandlerWithCollectingFilterBuilder<T> unmatchedEntryWriter(Writer<T> unmatchedEntryWriter) {
      this.unmatchedEntryWriter = unmatchedEntryWriter;
      return this;
    }

    public WritingDataHandlerWithCollectingFilter<T> build() {
      if (matchedEntryWriter == null) {
        matchedEntryWriter = new NoOpWriter<T>();
      }
      if (unmatchedEntryWriter == null) {
        unmatchedEntryWriter = new NoOpWriter<T>();
      }
      return new WritingDataHandlerWithCollectingFilter<T>(collectingFilter, matchedEntryWriter,
          unmatchedEntryWriter);
    }
  }

  @Override
  public String toString() {
    return "{Writing data handler with collecting filter}";
  }
}
