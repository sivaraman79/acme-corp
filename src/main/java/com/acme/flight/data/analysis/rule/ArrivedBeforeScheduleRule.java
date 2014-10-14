package com.acme.flight.data.analysis.rule;

import com.acme.flight.data.analysis.model.ArrivalInfo;

public class ArrivedBeforeScheduleRule implements Rule {

	@Override
	public boolean accepts(ArrivalInfo arrival) {
		return (arrival.getDelay() < 0);
	}

}
