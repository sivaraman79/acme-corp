package com.acme.flight.analysis.writer;

import java.io.IOException;

import com.acme.flight.analysis.model.ArrivalInfo;

public interface Writer {
	void write(ArrivalInfo flightInfo) throws IOException;
	void close() throws IOException;
}
