package com.acme.flight.analysis.custom.converter;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.acme.flight.analysis.model.ArrivalInfo;

public class ArrivalInfoSerializer extends JsonSerializer<ArrivalInfo> {

	private final DateTimeFormatter dtf = DateTimeFormat.forPattern(ArrivalInfo.INPUT_DATE_FORMAT);
	
	@Override
	public void serialize(ArrivalInfo arrivalInfo, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("date", dtf.print(arrivalInfo.getDateTime()));
		jsonGenerator.writeNumberField("delay", arrivalInfo.getDelay());
		jsonGenerator.writeEndObject();
	}

}
