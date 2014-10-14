package com.acme.flight.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.flight.analysis.input.reader.CsvReader;
import com.acme.flight.analysis.input.reader.Reader;
import com.acme.flight.analysis.model.ArrivalInfo;
import com.acme.flight.analysis.selection.strategy.ArrivalInfoHandler;
import com.acme.flight.analysis.selection.strategy.BestPerformanceArrivalInfoHandler;
import com.acme.flight.analysis.selection.strategy.DailyBestFlightArrivalInfoHandler;
import com.acme.flight.analysis.writer.CsvWriter;

public class Play {
	
    private static final Logger LOGGER = LogManager.getLogger();
    
    private List<ArrivalInfoHandler> handlers;

	public static void main(String[] args) throws IOException {
		LOGGER.info("Processing is about to start..");
		
		Play play = new Play();
		play.buildHandlers();
		
		Reader reader = new CsvReader(Play.class.getClassLoader().getResourceAsStream("flights.csv"));
		ArrivalInfo arrivalInfo;
		while ((arrivalInfo  = reader.read()) != null) {
			for (ArrivalInfoHandler handler : play.getHandlers()) {
				handler.handle(arrivalInfo);
			}
		}
		
		for (ArrivalInfoHandler handler : play.getHandlers()) {
			handler.postProcess();
		}
		
		LOGGER.info("Processing is complete..");
	}
	
	private void buildHandlers() throws IOException {
		LOGGER.debug("Building handlers..");
		handlers = new ArrayList<>();
		handlers.add(new BestPerformanceArrivalInfoHandler(new CsvWriter("bin/best_performance.csv")));
		handlers.add(new DailyBestFlightArrivalInfoHandler(new CsvWriter("bin/daily_best_flights.csv")));
		LOGGER.debug("Added best performance & daily best flight arrival info handlers..");
	}

	public List<ArrivalInfoHandler> getHandlers() {
		return handlers;
	}
	
}
