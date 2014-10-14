package com.acme.flight.data.analysis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.csveed.annotations.CsvCell;

/**
 * Represents a flight.
 *
 * Assumption: Uniqueness comes from the origin, destination & distanceInMiles fields
 *
 * @author Ashok.Koyi
 *
 */
public class Flight {

	@CsvCell(columnName = "origin")
	private String originCode;
	@CsvCell(columnName = "destination")
	private String destinationCode;
	@CsvCell(columnName = "distance")
	private int distanceInMiles;

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

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(originCode).append(destinationCode).hashCode();
	}

	@Override
	public boolean equals(Object that) {
	   if (that == null) { return false; }
	   if (that == this) { return true; }
	   if (that.getClass() != getClass()) {
		   return false;
	   }
	   Flight thatFlight = (Flight) that;
	   return new EqualsBuilder().append(originCode, thatFlight.getOriginCode()).append(destinationCode, thatFlight.getDestinationCode()).isEquals();
	}

	@Override
	public String toString() {
		return "Flight [originCode=" + originCode + ", destinationCode=" + destinationCode + ", distanceInMiles=" + distanceInMiles + "]";
	}

	public boolean isJourneyInMilesGreaterThan(int miles) {
		return (distanceInMiles > miles);
	}
}
