package com.acme.flight.analysis.selection.strategy;

import java.io.IOException;

import com.acme.flight.analysis.model.ArrivalInfo;

public interface ArrivalInfoHandler {
	void handle(ArrivalInfo arrival);
	void postProcess() throws IOException;
}
