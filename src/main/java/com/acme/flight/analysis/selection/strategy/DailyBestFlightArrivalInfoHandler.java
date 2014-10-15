package com.acme.flight.analysis.selection.strategy;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.acme.flight.analysis.model.ArrivalInfo;
import com.acme.flight.analysis.writer.Writer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class DailyBestFlightArrivalInfoHandler implements ArrivalInfoHandler {

	private static final Logger LOGGER = LogManager.getLogger();

	private ListMultimap<LocalDate, ArrivalInfo> dateToArrivalInfo = ArrayListMultimap.create();
	private Writer writer;
	
	public DailyBestFlightArrivalInfoHandler(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void handle(ArrivalInfo arrival) {
		LOGGER.debug("Handling arrival info : {}", arrival);
		if(!arrival.arrivedDuringnMorningHours()) {
			return;
		}
		List<ArrivalInfo> existingArrivals = dateToArrivalInfo.get(arrival.getDateTime().toLocalDate());
		// If element is already present, verify whether the new arrival is better than existing list
		if(existingArrivals.size() != 0) {
			ArrivalInfo firstArrivalInfo = existingArrivals.get(0);
			// If the delay is less than the existing entries by covering atleast the same distance, replace the existing set with this one
			if(arrival.delayLessThan(firstArrivalInfo)) {
				if(arrival.coversDistaceOfAtleast(firstArrivalInfo)) {
					dateToArrivalInfo.removeAll(arrival.getDateTime());
					dateToArrivalInfo.put(arrival.getDateTime().toLocalDate(), arrival);
				}
			} else if (arrival.delaySameAs(firstArrivalInfo)) {
				// If the delay is same as the existing entry & covers more distance than the existing entry, replace the existing set with this one
				if(arrival.coversMoreDistanceThan(firstArrivalInfo)) {
					dateToArrivalInfo.removeAll(arrival.getDateTime());
					dateToArrivalInfo.put(arrival.getDateTime().toLocalDate(), arrival);
				}
			} else if(arrival.delaySameAs(firstArrivalInfo) && arrival.coversSameDistaceAs(firstArrivalInfo)) {
				dateToArrivalInfo.put(arrival.getDateTime().toLocalDate(), arrival);
			}
		} else {
			dateToArrivalInfo.put(arrival.getDateTime().toLocalDate(), arrival);
		}
	}

	@Override
	public void postProcess() throws IOException {
		LOGGER.info("Post processing started for DailyBestFlightArrivalInfoHandler");
		for (LocalDate date : dateToArrivalInfo.keySet()) {
			List<ArrivalInfo> arrivalInfos = dateToArrivalInfo.get(date);
			// if there are more than one flight on the same date with the same delay & distance, the you can expect more than one arrival info
			for (ArrivalInfo arrivalInfo : arrivalInfos) {
				writer.write(arrivalInfo);
			}
		}
		writer.close();
		LOGGER.info("Written daily best flight arrival info to the output");
	}

}
