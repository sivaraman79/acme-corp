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

	private Writer bestPerformingArrivalInfoWriter;
	private Writer otherArrivalInfoWriter;

	private ListMultimap<Flight, ArrivalInfo> flightToArrivalInfo = ArrayListMultimap.create();
	private List<ArrivalInfo> ignoredArrivalInfos = new ArrayList<>();

	public BestPerformanceArrivalInfoHandler(Writer bestPerformingArrivalInfoWriter, Writer otherArrivalInfoWriter) {
		this.bestPerformingArrivalInfoWriter = bestPerformingArrivalInfoWriter;
		this.otherArrivalInfoWriter = otherArrivalInfoWriter;
	}

	@Override
	public void handle(ArrivalInfo arrival) {
		if (arrival.arrivedBeforeSchedule() && arrival.getFlight().isJourneyInMilesGreaterThan(MIN_JOURNEY_DISTANCE_IN_MILES)) {
			flightToArrivalInfo.put(arrival.getFlight(), arrival);
		} else {
			ignoredArrivalInfos.add(arrival);
		}
	}

	@Override
	public void postProcess() throws IOException {
		LOGGER.info("Post processing started for BestPerformanceArrivalInfoHandler..");

		for (Flight flight : flightToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivalInfos = flightToArrivalInfo.get(flight);
			if(arrivalInfos.size() < MIN_NUMBER_OF_ARRIVALS) {
				List<ArrivalInfo> removedArrivalInfos = flightToArrivalInfo.removeAll(flight);
				ignoredArrivalInfos.addAll(removedArrivalInfos);
			}
		}

		LOGGER.info("Saving best performing flight arrival info..");
		for (Flight flight : flightToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivalInfos = flightToArrivalInfo.get(flight);
			for (ArrivalInfo arrivalInfo : arrivalInfos) {
				bestPerformingArrivalInfoWriter.write(arrivalInfo);
			}
		}
		bestPerformingArrivalInfoWriter.close();

		LOGGER.info("Best performing flight arrival info save complete.");

		LOGGER.info("Saving sub-optimal flight arrival info ...");
		for (ArrivalInfo ignoredArrivalInfo : ignoredArrivalInfos) {
			otherArrivalInfoWriter.write(ignoredArrivalInfo);
		}

		otherArrivalInfoWriter.close();
		LOGGER.info("Sub-optimal arrival info save complete.");

		LOGGER.info("Post processing complete for BestPerformanceArrivalInfoHandler");
	}
}
