package com.acme.flight.analysis.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.model.Arrival;
import com.acme.flight.analysis.model.Flight;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Filters the data based on the following rules
 * 
 * <ul>
 * <li>The flight must have reached before scheduled time</li>
 * <li>It must have reached before scheduled time at least 2 times</li>
 * <li>The flight distance must be more than 400 miles</li>
 * </ul>
 * 
 * @author thekalinga
 *
 */
public class BestPerformingArrivalsCollectingFilter implements CollectingFilter<Arrival> {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final int MIN_NUMBER_OF_ARRIVALS = 2;
  private static final int MIN_JOURNEY_DISTANCE_IN_MILES = 400;

  private ListMultimap<Flight, Arrival> flightToArrival = ArrayListMultimap.create();
  private List<Arrival> unmatchedArrivals = new ArrayList<>();

  @Override
  public void apply(Arrival arrival) {
    if (arrival.arrivedBeforeSchedule()
        && arrival.getFlight().isJourneyInMilesGreaterThan(MIN_JOURNEY_DISTANCE_IN_MILES)) {
      flightToArrival.put(arrival.getFlight(), arrival);
    } else {
      unmatchedArrivals.add(arrival);
    }
  }

  @Override
  public void processCollectedEntries() {
    LOGGER.info("Processing collected entries for BestPerformingArrivalsCollectingFilter..");

    for (Flight flight : flightToArrival.keySet()) {
      List<Arrival> arrivalInfos = flightToArrival.get(flight);
      if (arrivalInfos.size() < MIN_NUMBER_OF_ARRIVALS) {
        List<Arrival> removedArrivalInfos = flightToArrival.removeAll(flight);
        unmatchedArrivals.addAll(removedArrivalInfos);
      }
    }
    LOGGER.info("Entry processing complete.");
  }

  @Override
  public Collection<Arrival> matchedEntries() {
    return flightToArrival.values();
  }

  @Override
  public Collection<Arrival> unmatchedEntries() {
    return unmatchedArrivals;
  }

  @Override
  public String toString() {
    return "{Best performing arrivals collecting filter}";
  }

}
