package com.acme.flight.data.analysis.rule;

import com.acme.flight.data.analysis.model.ArrivalInfo;

public interface Rule {
	boolean accepts(ArrivalInfo arrival);
}
