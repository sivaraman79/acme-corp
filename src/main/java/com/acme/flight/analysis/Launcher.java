package com.acme.flight.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.exception.ProcessingFailedException;
import com.acme.flight.analysis.filter.BestPerformingArrivalsCollectingFilter;
import com.acme.flight.analysis.filter.DailyBestArrivalsCollectingFilter;
import com.acme.flight.analysis.handler.DataHandler;
import com.acme.flight.analysis.handler.WritingDataHandlerWithCollectingFilter;
import com.acme.flight.analysis.handler.WritingDataHandlerWithCollectingFilter.WritingDataHandlerWithCollectingFilterBuilder;
import com.acme.flight.analysis.model.Arrival;
import com.acme.flight.analysis.reader.CsveedReader;
import com.acme.flight.analysis.reader.Reader;
import com.acme.flight.analysis.writer.CsveedWriter;
import com.acme.flight.analysis.writer.JacksonJsonWriter;

/**
 * A bridge that connects
 * 
 * <ol>
 * <li>reader to handler</li>
 * <li>data handler to data filter</li>
 * <li>data handler to writer</li>
 * </ol>
 * 
 * @author thekalinga
 *
 */
public class Launcher {

  private static final Logger LOGGER = LogManager.getLogger();

  private List<DataHandler<Arrival>> dataHandlers;

  public static void main(String[] args) throws ProcessingFailedException, IOException {
    LOGGER.info("Processing is about to start..");

    Launcher launcher = new Launcher();
    launcher.buildDataHandlers();

    Reader<Arrival> reader =
        new CsveedReader<>(Launcher.class.getClassLoader().getResourceAsStream("flights.csv"),
            Arrival.class);

    // TODO identify why the CSVeed reader is not functioning properly with flight.csv, so that we
    // can avoid this null check
    Arrival arrivalInfo;
    while ((arrivalInfo = reader.read()) != null) {
      for (DataHandler<Arrival> handler : launcher.getDataHandlers()) {
        handler.handle(arrivalInfo);
      }
    }

    for (DataHandler<Arrival> handler : launcher.getDataHandlers()) {
      handler.postProcess();
    }

    LOGGER.info("Processing is complete..");
  }

  /**
   * Builds all the handlers that are expected to handle the input
   * 
   * @throws IOException
   */
  private void buildDataHandlers() throws IOException {
    LOGGER.debug("Building handlers..");
    dataHandlers = new ArrayList<>();
    WritingDataHandlerWithCollectingFilter<Arrival> bestPerformingArrivalHandler =
        new WritingDataHandlerWithCollectingFilterBuilder<Arrival>(
            new BestPerformingArrivalsCollectingFilter())
            .matchedEntryWriter(new JacksonJsonWriter<>("bin/best_performance.json", Arrival.class))
            .unmatchedEntryWriter(new CsveedWriter<>("bin/other_flights.csv", Arrival.class))
            .build();

    WritingDataHandlerWithCollectingFilter<Arrival> dailyBestFlightArrivalHandler =
        new WritingDataHandlerWithCollectingFilterBuilder<Arrival>(
            new DailyBestArrivalsCollectingFilter()).matchedEntryWriter(
            new CsveedWriter<>("bin/daily_best_flights.csv", Arrival.class)).build();

    dataHandlers.add(bestPerformingArrivalHandler);
    dataHandlers.add(dailyBestFlightArrivalHandler);
    LOGGER.debug("Added best performance, sub-optimal & daily best flight arrival info handlers..");
  }

  public List<DataHandler<Arrival>> getDataHandlers() {
    return dataHandlers;
  }

}
