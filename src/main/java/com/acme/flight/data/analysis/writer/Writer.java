package com.acme.flight.data.analysis.writer;

import com.acme.flight.data.analysis.model.ArrivalInfo;

public interface Writer {
	void write(ArrivalInfo flightInfo);
	void close();
}
