package com.acme.flight.data.analysis.selection.strategy;

import com.acme.flight.data.analysis.model.ArrivalInfo;

public interface ArrivalInfoHandler {
	void handle(ArrivalInfo arrival);
	void postProcess();
}
