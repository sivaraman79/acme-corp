package com.acme.flight.analysis.rule;

import com.acme.flight.analysis.model.ArrivalInfo;

public interface Rule {
	boolean accepts(ArrivalInfo arrival);
}
