package com.acme.flight.analysis.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.acme.flight.analysis.model.Arrival;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Will return single best arrival on each day based on the following rules
 *
 * <ul>
 * <li>Delay is be minimum</li>
 * <li>Distance is maximum</li>
 * <li>Flight is in morning hours</li>
 * </ul>
 *
 * @author thekalinga
 *
 */
public class DailyBestArrivalsCollectingFilter implements CollectingFilter<Arrival> {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final List<Arrival> EMPTY_ARRIVAL_LIST = Collections
      .unmodifiableList(new ArrayList<Arrival>());

  private ListMultimap<LocalDate, Arrival> dateToArrival = ArrayListMultimap.create();

  @Override
  public void apply(Arrival arrival) {
    LOGGER.debug("Handling arrival info : {}", arrival);
    if (!arrival.arrivedDuringnMorningHours()) {
      return;
    }
    List<Arrival> existingArrivals = dateToArrival.get(arrival.getDateTime().toLocalDate());
    // If element is already present, verify whether the new arrival is better than existing list
    if (existingArrivals.size() != 0) {
      Arrival firstArrivalInfo = existingArrivals.get(0);
      // If the delay is less than the existing entries by covering atleast the same distance,
      // replace the existing set with this one
      if (arrival.delayLessThan(firstArrivalInfo)) {
        if (arrival.coversDistaceOfAtleast(firstArrivalInfo)) {
          dateToArrival.removeAll(arrival.getDateTime());
          dateToArrival.put(arrival.getDateTime().toLocalDate(), arrival);
        }
      } else if (arrival.delaySameAs(firstArrivalInfo)) {
        // If the delay is same as the existing entry & covers more distance than the existing
        // entry, replace the existing set with this one
        if (arrival.coversMoreDistanceThan(firstArrivalInfo)) {
          dateToArrival.removeAll(arrival.getDateTime());
          dateToArrival.put(arrival.getDateTime().toLocalDate(), arrival);
        }
      } else if (arrival.delaySameAs(firstArrivalInfo)
          && arrival.coversSameDistaceAs(firstArrivalInfo)) {
        dateToArrival.put(arrival.getDateTime().toLocalDate(), arrival);
      }
    } else {
      dateToArrival.put(arrival.getDateTime().toLocalDate(), arrival);
    }
  }

  @Override
  public void processCollectedEntries() {}

  @Override
  public Collection<Arrival> matchedEntries() {
    return dateToArrival.values();
  }

  /**
   * Current implementation always returns empty list as we dont care about this for now. Actual
   * implementation
   */
  @Override
  public Collection<Arrival> unmatchedEntries() {
    return EMPTY_ARRIVAL_LIST;
  }

  @Override
  public String toString() {
    return "{Daily best arrivals collecting filter}";
  }

}
