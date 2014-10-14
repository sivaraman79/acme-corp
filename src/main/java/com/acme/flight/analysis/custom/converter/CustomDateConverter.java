package com.acme.flight.analysis.custom.converter;

import org.csveed.bean.conversion.AbstractConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CustomDateConverter extends AbstractConverter<LocalDate> {

	private static final int ARIIVAL_YEAR = 2001;
	private final DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMmmss");

	public CustomDateConverter(Class<LocalDate> clazz) {
		super(clazz);
	}

	@Override
	public LocalDate fromString(String input) throws Exception {
		return dtf.parseLocalDate(input).withYear(ARIIVAL_YEAR);
	}

	@Override
	public String toString(LocalDate inputDate) throws Exception {
		return dtf.print(inputDate);
	}

}
