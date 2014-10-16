package com.acme.flight.analysis.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;

/**
 * CSVeed library based implementation for reading the data from a csv file
 * 
 * @author thekalinga
 *
 * @param <T> Input type to be read
 */
public class CsveedReader<T> implements Reader<T> {

  private CsvClient<T> csvClient;

  public CsveedReader(String path, Class<T> tClazz) throws FileNotFoundException {
    csvClient = new CsvClientImpl<T>(new BufferedReader(new FileReader(new File(path))), tClazz);
  }

  public CsveedReader(InputStream inputStream, Class<T> tClazz) {
    csvClient =
        new CsvClientImpl<T>(new BufferedReader(new InputStreamReader(inputStream)), tClazz);
  }

  @Override
  public T read() {
    return csvClient.readBean();
  }

  @Override
  public boolean hasMoreItems() {
    return !csvClient.isFinished();
  }

}
