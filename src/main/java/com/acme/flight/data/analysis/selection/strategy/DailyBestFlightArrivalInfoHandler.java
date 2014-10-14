package com.acme.flight.data.analysis.selection.strategy;

import java.util.List;

import org.joda.time.LocalDate;

import com.acme.flight.data.analysis.model.ArrivalInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class DailyBestFlightArrivalInfoHandler implements ArrivalInfoHandler {

	private ListMultimap<LocalDate, ArrivalInfo> dateToArrivalInfo = ArrayListMultimap.create();

	@Override
	public void handle(ArrivalInfo arrival) {
		List<ArrivalInfo> existingArrivals = dateToArrivalInfo.get(arrival.getDate());
		// If element is already present, verify whether the new arrival is better than existing list
		if(existingArrivals.size() != 0) {
			ArrivalInfo firstArrival = existingArrivals.get(0);
			if(arrival.getDelay() < firstArrival.getDelay()) {
				dateToArrivalInfo.removeAll(arrival.getDate());
			}
		} else {
			dateToArrivalInfo.put(arrival.getDate(), arrival);
		}
	}

	@Override
	public void postProcess() {

	}

}
