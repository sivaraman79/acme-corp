package com.acme.flight.analysis.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.model.ArrivalInfo;
import com.acme.flight.analysis.model.Flight;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class BestPerformanceArrivalInfoFilter implements InMemoryFilter<ArrivalInfo> {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final int MIN_NUMBER_OF_ARRIVALS = 2;
  private static final int MIN_JOURNEY_DISTANCE_IN_MILES = 400;

  private ListMultimap<Flight, ArrivalInfo> flightToArrivalInfo = ArrayListMultimap.create();
  private List<ArrivalInfo> ignoredArrivalInfos = new ArrayList<>();

  @Override
  public Collection<ArrivalInfo> getSelectedArrivalInfos() {
    return flightToArrivalInfo.values();
  }

  @Override
  public List<ArrivalInfo> getIgnoreArrivalInfos() {
    return ignoredArrivalInfos;
  }

  @Override
  public void filter(ArrivalInfo arrival) {
    if (arrival.arrivedBeforeSchedule()
        && arrival.getFlight().isJourneyInMilesGreaterThan(MIN_JOURNEY_DISTANCE_IN_MILES)) {
      flightToArrivalInfo.put(arrival.getFlight(), arrival);
    } else {
      ignoredArrivalInfos.add(arrival);
    }
  }

  @Override
  public void postProcess() {
    LOGGER.info("Post processing started for BestPerformanceArrivalInfoHandler..");

    for (Flight flight : flightToArrivalInfo.keySet()) {
      List<ArrivalInfo> arrivalInfos = flightToArrivalInfo.get(flight);
      if (arrivalInfos.size() < MIN_NUMBER_OF_ARRIVALS) {
        List<ArrivalInfo> removedArrivalInfos = flightToArrivalInfo.removeAll(flight);
        ignoredArrivalInfos.addAll(removedArrivalInfos);
      }
    }

  }

}
