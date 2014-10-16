package com.acme.flight.analysis.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;
import org.csveed.bean.ColumnNameMapper;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.acme.flight.analysis.util.CsveedArrivalDateConverter;
import com.acme.flight.analysis.util.LocalDateTimeJacksonSerializer;

/**
 * Represents a single instance of flight arrival
 *
 * @author Ashok.Koyi
 *
 */
@CsvFile(mappingStrategy = ColumnNameMapper.class, separator = ',')
public class Arrival implements Serializable {

  private static final long serialVersionUID = -9188218907330342463L;

  public static final String INPUT_DATE_FORMAT = "MMddHHmm";
  public static final int ARIIVAL_YEAR = 2001;

  @CsvIgnore
  private Flight flight;

  @CsvConverter(converter = CsveedArrivalDateConverter.class)
  @CsvCell(columnName = "date")
  private LocalDateTime arrivedAt;
  @CsvCell(columnName = "origin")
  private String originCode;
  @CsvCell(columnName = "destination")
  private String destinationCode;
  @CsvCell(columnName = "distance")
  private int distanceInMiles;
  private int delay;

  @JsonProperty("distance")
  public int getDistanceInMiles() {
    return distanceInMiles;
  }

  public void setDistanceInMiles(int distanceInMiles) {
    this.distanceInMiles = distanceInMiles;
  }

  @JsonProperty("origin")
  public String getOriginCode() {
    return originCode;
  }

  public void setOriginCode(String originCode) {
    this.originCode = originCode;
  }

  @JsonProperty("destination")
  public String getDestinationCode() {
    return destinationCode;
  }

  public void setDestinationCode(String destinationCode) {
    this.destinationCode = destinationCode;
  }

  @JsonProperty("date")
  @JsonSerialize(using = LocalDateTimeJacksonSerializer.class)
  public LocalDateTime getArrivedAt() {
    return arrivedAt;
  }

  public void setArrivedAt(LocalDateTime arrivedAt) {
    this.arrivedAt = arrivedAt;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  @JsonIgnore
  public Flight getFlight() {
    // TODO : Temp hack for now till I identify a better mechanism to use csveed library to be able
    // to read properties into associations
    if (flight == null) {
      flight = new Flight(originCode, destinationCode, distanceInMiles);
    }
    return flight;
  }

  // Utility methods

  public boolean arrivedBeforeSchedule() {
    return (delay < 0);
  }

  public boolean delayLessThan(Arrival arrivalInfo) {
    return (delay < arrivalInfo.delay);
  }

  public boolean delaySameAs(Arrival arrivalInfo) {
    return (delay == arrivalInfo.delay);
  }

  public boolean coversMoreDistanceThan(Arrival arrivalInfo) {
    return (distanceInMiles > arrivalInfo.distanceInMiles);
  }

  public boolean coversDistaceOfAtleast(Arrival arrivalInfo) {
    return (distanceInMiles >= arrivalInfo.distanceInMiles);
  }

  public boolean coversSameDistaceAs(Arrival arrivalInfo) {
    return (distanceInMiles == arrivalInfo.distanceInMiles);
  }

  public boolean arrivedDuringnMorningHours() {
    return arrivedAt.toLocalTime().isBefore(new LocalTime(12, 00));
  }

  @Override
  public String toString() {
    return "ArrivalInfo [arrivedAt=" + arrivedAt + ", flight=" + getFlight() + ", delay=" + delay
        + "]";
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(originCode).append(destinationCode).append(arrivedAt)
        .hashCode();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) {
      return false;
    }
    if (that == this) {
      return true;
    }
    if (that.getClass() != getClass()) {
      return false;
    }
    Arrival thatArrival = (Arrival) that;
    return new EqualsBuilder().append(originCode, thatArrival.getOriginCode())
        .append(destinationCode, thatArrival.getDestinationCode())
        .append(arrivedAt, thatArrival.getArrivedAt()).isEquals();
  }


}
