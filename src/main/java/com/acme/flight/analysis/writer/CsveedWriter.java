package com.acme.flight.analysis.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;
import org.csveed.bean.ColumnNameMapper;

// TODO Need to take care of order in which the columns are written. This will not matter if we use
// column name mapper while reading
public class CsveedWriter<T> implements Writer<T> {

  private CsvClient<T> csvWriter;
  private BufferedWriter internalWriter;
  private boolean isClosed = false;

  public CsveedWriter(String path, Class<T> tClazz) throws IOException {
    internalWriter = new BufferedWriter(new FileWriter(new File(path)));
    csvWriter = new CsvClientImpl<T>(internalWriter, tClazz).setMapper(ColumnNameMapper.class);
  }

  @Override
  public void write(T t) throws IOException {
    if (isClosed) {
      throw new IllegalStateException("Cannot write to an already closed stream");
    }
    csvWriter.writeBean(t);
  }

  @Override
  public void close() throws IOException {
    internalWriter.flush();
    internalWriter.close();
    isClosed = true;
  }
  
  @Override
  public String toString() {
    return "{Csveed writer}";
  }

}
