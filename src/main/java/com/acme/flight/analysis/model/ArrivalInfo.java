package com.acme.flight.analysis.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;
import org.csveed.bean.ColumnNameMapper;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.acme.flight.analysis.custom.converter.CsvDateConverter;

/**
 * Represents a single instance of flight arrival
 *
 * @author Ashok.Koyi
 *
 */
@CsvFile(mappingStrategy = ColumnNameMapper.class, separator = ',')
//@JsonSerialize(using = ArrivalInfoSerializer.class)
@JsonAutoDetect
public class ArrivalInfo implements Serializable {

	private static final long serialVersionUID = -9188218907330342463L;

	public static final String INPUT_DATE_FORMAT = "MMddmmss";
	public static final int ARIIVAL_YEAR = 2001;

	@CsvIgnore
	private int arrivalId;

	@CsvIgnore
	private Flight flight;

	@CsvConverter(converter = CsvDateConverter.class)
	@CsvCell(columnName = "date")
	private LocalDateTime dateTime;
	@CsvCell(columnName = "origin")
	private String originCode;
	@CsvCell(columnName = "destination")
	private String destinationCode;
	@CsvCell(columnName = "distance")
	private int distanceInMiles;
	private int delay;

	@JsonValue
	@JsonProperty("distance")
	public int getDistanceInMiles() {
		return distanceInMiles;
	}

	public void setDistanceInMiles(int distanceInMiles) {
		this.distanceInMiles = distanceInMiles;
	}

	@JsonValue
	@JsonProperty("origin")
	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	@JsonValue
	@JsonProperty("destination")
	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	
	@JsonValue
	@JsonProperty("date")
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	@JsonValue
	public int getDelay() {
		return delay;
	}

	@JsonValue
	public void setDelay(int delay) {
		this.delay = delay;
	}

	@JsonIgnore
	public Flight getFlight() {
		// TODO : Temp hack for now till I identify a better mechanism to use csveed library to be able to read properties into associations
		if(flight == null) {
			flight = new Flight(originCode, destinationCode, distanceInMiles);
		}
		return flight;
	}

	@JsonIgnore
	public int getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	// Utility methods

	public boolean arrivedBeforeSchedule() {
		return (delay < 0);
	}
	
	public boolean delayLessThan(ArrivalInfo arrivalInfo) {
		return (delay < arrivalInfo.delay);
	}
	
	public boolean delaySameAs(ArrivalInfo arrivalInfo) {
		return (delay == arrivalInfo.delay);
	}
	
	public boolean coversMoreDistanceThan(ArrivalInfo arrivalInfo) {
		return (distanceInMiles < arrivalInfo.distanceInMiles);
	}
	
	public boolean coversDistaceOfAtleast(ArrivalInfo arrivalInfo) {
		return (distanceInMiles >= arrivalInfo.distanceInMiles);
	}
	
	public boolean coversSameDistaceAs(ArrivalInfo arrivalInfo) {
		return (distanceInMiles == arrivalInfo.distanceInMiles);
	}
	
	public boolean arrivedDuringnMorningHours() {
		return dateTime.toLocalTime().isBefore(new LocalTime(12, 00));
	}
	
	@Override
	public String toString() {
		return "ArrivalInfo [arrivalId=" + arrivalId + ", dateTime=" + dateTime + ", flight=" + flight + ", delay=" + delay + "]";
	}

}