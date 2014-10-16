package com.acme.flight.analysis.model;

import static org.junit.Assert.*;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class ArrivalTests {

  private Arrival baseArrival;

  @Before
  public void setUp() throws Exception {
    baseArrival = new Arrival();
  }

  @Test
  @SuppressWarnings("serial")
  public void testArrivedDuringnMorningHours() {
    Arrival arrival = new Arrival() {
      {
        setArrivedAt(new LocalDateTime(2001, 03, 01, 10, 00));
      }
     };
    assertTrue(arrival.arrivedDuringnMorningHours());
    arrival.setArrivedAt(arrival.getArrivedAt().plusHours(10));
    assertFalse(arrival.arrivedDuringnMorningHours());
    arrival.setArrivedAt(arrival.getArrivedAt().minusHours(8));
    assertFalse(arrival.arrivedDuringnMorningHours());
  }

}
