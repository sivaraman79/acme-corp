package com.acme.flight.data.analysis.model;

import org.csveed.annotations.CsvConverter;
import org.joda.time.LocalDate;

import com.acme.flight.analysis.custom.converter.CustomDateConverter;

/**
 * Represents a single instance of flight arrival
 *
 * @author Ashok.Koyi
 *
 */
public class ArrivalInfo {

	private long arrivalId;

	@CsvConverter(converter = CustomDateConverter.class)
	private LocalDate date;
	private Flight flight;
	private int delay;

	public LocalDate getDate() {
		return date;
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

	public long getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(long arrivalId) {
		this.arrivalId = arrivalId;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isBeforeSchedule() {
		return (delay < 0);
	}

	@Override
	public String toString() {
		return "ArrivalInfo [arrivalId=" + arrivalId + ", date=" + date + ", flight=" + flight + ", delay=" + delay + "]";
	}

}