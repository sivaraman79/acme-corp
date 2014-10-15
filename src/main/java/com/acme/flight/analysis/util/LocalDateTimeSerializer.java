package com.acme.flight.analysis.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.acme.flight.analysis.model.ArrivalInfo;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	private final DateTimeFormatter dtf = DateTimeFormat.forPattern(ArrivalInfo.INPUT_DATE_FORMAT);

	@Override
	public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
		jsonGenerator.writeNumber(dtf.print(localDateTime));
	}

}
