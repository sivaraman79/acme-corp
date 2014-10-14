package com.acme.flight.data.analysis.selection.strategy;

import java.util.List;

import com.acme.flight.data.analysis.model.ArrivalInfo;
import com.acme.flight.data.analysis.model.Flight;
import com.acme.flight.data.analysis.writer.Writer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class BestPerformanceArrivalInfoHandler implements ArrivalInfoHandler {

	private Writer writer;

	private static final int MIN_NUMBER_OF_ARRIVALS = 2;
	private static final int MIN_JOURNEY_DISTANCE_IN_MILES = 400;

	private ListMultimap<Flight, ArrivalInfo> flightToArrivalInfo = ArrayListMultimap.create();
	private boolean isProcessed = false;

	public BestPerformanceArrivalInfoHandler(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void handle(ArrivalInfo arrival) {
		if(isProcessed) {
			throw new IllegalStateException("Cannot be invoked after invoking postProcess() method");
		}
		if (arrival.isBeforeSchedule() && arrival.getFlight().isJourneyInMilesGreaterThan(MIN_JOURNEY_DISTANCE_IN_MILES)) {
			flightToArrivalInfo.put(arrival.getFlight(), arrival);
		}
	}

	@Override
	public void postProcess() {
		for (Flight flight : flightToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivals = flightToArrivalInfo.get(flight);
			if(arrivals.size() < MIN_NUMBER_OF_ARRIVALS) {
				flightToArrivalInfo.removeAll(flight);
			}
		}
		isProcessed = true;
	}

//	@Override
//	public Collection<ArrivalInfo> selectedArrivals() {
//		if(!isProcessed) {
//			throw new IllegalStateException("postProcess() must be invoked before invoking this method");
//		}
//		return flightToArrivalInfo.values();
//	}

}
