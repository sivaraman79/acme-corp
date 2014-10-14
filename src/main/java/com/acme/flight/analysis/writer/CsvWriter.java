package com.acme.flight.analysis.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;
import org.csveed.bean.ColumnNameMapper;

import com.acme.flight.analysis.model.ArrivalInfo;

public class CsvWriter implements Writer {

	private CsvClient<ArrivalInfo> csvWriter;
	private BufferedWriter internalWriter;
	private boolean isClosed = false;

	public CsvWriter(String path) throws IOException {
		internalWriter = new BufferedWriter(new FileWriter(new File(path)));
		csvWriter = new CsvClientImpl<ArrivalInfo>(internalWriter, ArrivalInfo.class).setMapper(ColumnNameMapper.class);
	}

	@Override
	public void write(ArrivalInfo arrivalInfo) {
		if(isClosed) {
			throw new IllegalStateException("Cannot write to an already closed stream");
		}
		csvWriter.writeBean(arrivalInfo);
	}

	@Override
	public void close() throws IOException {
		internalWriter.flush();
		internalWriter.close();
		isClosed = true;
	}

}
