package com.acme.flight.analysis.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.acme.flight.analysis.model.ArrivalInfo;

public class JsonWriter implements Writer {
	
	private final DateTimeFormatter dtf = DateTimeFormat.forPattern(ArrivalInfo.INPUT_DATE_FORMAT);

	private JsonGenerator jsonGenerator;

	public JsonWriter(String path) throws IOException {
		jsonGenerator = new JsonFactory().createJsonGenerator(new BufferedOutputStream(new FileOutputStream(new File(path))), JsonEncoding.UTF8);
		jsonGenerator.writeStartArray();
	}

	@Override
	public void write(ArrivalInfo arrivalInfo) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("date", dtf.print(arrivalInfo.getDateTime()));
		jsonGenerator.writeNumberField("delay", arrivalInfo.getDelay());
		jsonGenerator.writeStringField("origin", arrivalInfo.getOriginCode());
		jsonGenerator.writeStringField("destination", arrivalInfo.getDestinationCode());
		jsonGenerator.writeNumberField("distance", arrivalInfo.getDistanceInMiles());
		jsonGenerator.writeEndObject();
	}

	@Override
	public void close() throws IOException {
		jsonGenerator.writeEndArray();
		jsonGenerator.close();
	}

}
