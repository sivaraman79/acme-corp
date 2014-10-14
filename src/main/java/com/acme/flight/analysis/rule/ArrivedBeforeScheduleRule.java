package com.acme.flight.analysis.rule;

import com.acme.flight.analysis.model.ArrivalInfo;

public class ArrivedBeforeScheduleRule implements Rule {

	@Override
	public boolean accepts(ArrivalInfo arrival) {
		return (arrival.getDelay() < 0);
	}

}
