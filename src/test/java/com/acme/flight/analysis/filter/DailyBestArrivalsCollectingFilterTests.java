package com.acme.flight.analysis.filter;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.acme.flight.analysis.Launcher;
import com.acme.flight.analysis.model.Arrival;
import com.acme.flight.analysis.reader.CsveedReader;
import com.acme.flight.analysis.reader.Reader;

public class DailyBestArrivalsCollectingFilterTests {

  private DailyBestArrivalsCollectingFilter dailyBestArrivalsCollectingFilter =
      new DailyBestArrivalsCollectingFilter();
  private Reader<Arrival> reader;

  @Before
  public void init() {
    reader =
        new CsveedReader<>(Launcher.class.getClassLoader().getResourceAsStream(
            "best-flight-test-data.csv"), Arrival.class);
    while (reader.hasMoreItems()) {
      dailyBestArrivalsCollectingFilter.apply(reader.read());
    }
  }

  @Test
  public void matchedEntriesReturnsOnlyDailyBestArrivals() {
    Reader<Arrival> expectedEntryReader =
        new CsveedReader<>(Launcher.class.getClassLoader().getResourceAsStream(
            "best-flight-test-expected-matched-entries.csv"), Arrival.class);
    Set<Arrival> expectedEntries = new HashSet<>();
    while (expectedEntryReader.hasMoreItems()) {
      expectedEntries.add(expectedEntryReader.read());
    }
    Collection<Arrival> matchedEntries = dailyBestArrivalsCollectingFilter.matchedEntries();
    System.out.println(matchedEntries);
    assertEquals(matchedEntries.size(), 5);
    assertEquals(expectedEntries, new HashSet<>(matchedEntries));
  }

}
