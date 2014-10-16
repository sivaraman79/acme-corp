package com.acme.flight.analysis.util;

import org.csveed.bean.conversion.AbstractConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.acme.flight.analysis.model.Arrival;

/**
 * Converter to help CSVeed library to convert transform String <-> LocalDateTime for arrivals
 * 
 * @author thekalinga
 *
 */
public class CsveedArrivalDateConverter extends AbstractConverter<LocalDateTime> {

  private final DateTimeFormatter dtf = DateTimeFormat.forPattern(Arrival.INPUT_DATE_FORMAT);

  public CsveedArrivalDateConverter() {
    super(LocalDateTime.class);
  }

  @Override
  public LocalDateTime fromString(String input) throws Exception {
    return dtf.parseLocalDateTime(input).withYear(Arrival.ARIIVAL_YEAR);
  }

  @Override
  public String toString(LocalDateTime inputDate) throws Exception {
    return dtf.print(inputDate);
  }

}
