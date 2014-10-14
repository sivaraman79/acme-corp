package com.acme.flight.analysis.selection.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.model.ArrivalInfo;
import com.acme.flight.analysis.model.Flight;
import com.acme.flight.analysis.writer.Writer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class BestPerformanceArrivalInfoHandler implements ArrivalInfoHandler {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final int MIN_NUMBER_OF_ARRIVALS = 2;
	private static final int MIN_JOURNEY_DISTANCE_IN_MILES = 400;

	private Writer writer;
	
	private ListMultimap<Flight, ArrivalInfo> flightToArrivalInfo = ArrayListMultimap.create();
	// TODO Write this to output
	private List<ArrivalInfo> ignoredArrivalInfos = new ArrayList<>();
	
	public BestPerformanceArrivalInfoHandler(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void handle(ArrivalInfo arrival) {
		if (arrival.isBeforeSchedule() && arrival.getFlight().isJourneyInMilesGreaterThan(MIN_JOURNEY_DISTANCE_IN_MILES)) {
			flightToArrivalInfo.put(arrival.getFlight(), arrival);
		} else {
			ignoredArrivalInfos.add(arrival);
		}
	}

	@Override
	public void postProcess() throws IOException {
		LOGGER.info("Post processing started for BestPerformanceArrivalInfoHandler");
		
		for (Flight flight : flightToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivalInfos = flightToArrivalInfo.get(flight);
			if(arrivalInfos.size() < MIN_NUMBER_OF_ARRIVALS) {
				List<ArrivalInfo> removedArrivalInfos = flightToArrivalInfo.removeAll(flight);
				ignoredArrivalInfos.addAll(removedArrivalInfos);
			}
		}
		
		for (Flight flight : flightToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivalInfos = flightToArrivalInfo.get(flight);
			for (ArrivalInfo arrivalInfo : arrivalInfos) {
				writer.write(arrivalInfo);
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			LOGGER.error("Issue while closing the writer.");
			throw e;
		}
	}
}
