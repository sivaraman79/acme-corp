package com.acme.flight.analysis.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;
import org.csveed.bean.ColumnNameMapper;
import org.joda.time.LocalDateTime;

import com.acme.flight.analysis.custom.converter.CustomDateConverter;

/**
 * Represents a single instance of flight arrival
 *
 * @author Ashok.Koyi
 *
 */
@CsvFile(mappingStrategy = ColumnNameMapper.class, separator = ',')
public class ArrivalInfo {

	@CsvIgnore
	private int arrivalId;

	@CsvIgnore
	private Flight flight;

	@CsvConverter(converter = CustomDateConverter.class)
	@CsvCell(columnName = "date")
	private LocalDateTime dateTime;
	@CsvCell(columnName = "origin")
	private String originCode;
	@CsvCell(columnName = "destination")
	private String destinationCode;
	@CsvCell(columnName = "distance")
	private int distanceInMiles;
	private int delay;

	public int getDistanceInMiles() {
		return distanceInMiles;
	}

	public void setDistanceInMiles(int distanceInMiles) {
		this.distanceInMiles = distanceInMiles;
	}

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public int getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(int arrivalId) {
		this.arrivalId = arrivalId;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	// Temp hack till I identify a better way of handling inner properties with csveed library
	public void initFlight() {
		this.flight = new Flight(originCode, destinationCode, distanceInMiles);
	}

	public boolean isBeforeSchedule() {
		return (delay < 0);
	}

	@Override
	public String toString() {
		return "ArrivalInfo [arrivalId=" + arrivalId + ", dateTime=" + dateTime + ", flight=" + flight + ", delay=" + delay + "]";
	}

}