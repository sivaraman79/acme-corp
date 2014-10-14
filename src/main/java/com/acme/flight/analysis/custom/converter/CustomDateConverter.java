package com.acme.flight.analysis.custom.converter;

import org.csveed.bean.conversion.AbstractConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CustomDateConverter extends AbstractConverter<LocalDateTime> {

	private static final String INPUT_DATE_FORMAT = "MMddmmss";
	private static final int ARIIVAL_YEAR = 2001;
	private final DateTimeFormatter dtf = DateTimeFormat.forPattern(INPUT_DATE_FORMAT);

	public CustomDateConverter() {
		super(LocalDateTime.class);
	}
	
	@Override
	public LocalDateTime fromString(String input) throws Exception {
		return dtf.parseLocalDateTime(input).withYear(ARIIVAL_YEAR);
	}

	@Override
	public String toString(LocalDateTime inputDate) throws Exception {
		return dtf.print(inputDate);
	}

}
