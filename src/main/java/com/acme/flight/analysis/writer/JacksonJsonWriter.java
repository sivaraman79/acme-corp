package com.acme.flight.analysis.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonJsonWriter<T> implements Writer<T> {

  private JsonGenerator jsonGenerator;
  private ObjectMapper mapper;

  public JacksonJsonWriter(String path, Class<T> tClazz) throws IOException {
    jsonGenerator =
        new JsonFactory().createJsonGenerator(new BufferedOutputStream(new FileOutputStream(
            new File(path))), JsonEncoding.UTF8);
    jsonGenerator.writeStartArray();
    mapper = new ObjectMapper();
  }
  
  @Override
  public void write(T t) throws IOException {
    // TODO Auto-generated method stub
    mapper.writeValue(jsonGenerator, t);
  }

  @Override
  public void close() throws IOException {
    jsonGenerator.writeEndArray();
    jsonGenerator.close();
  }

  @Override
  public String toString() {
    return "{Jackson json writer}";
  }
}
