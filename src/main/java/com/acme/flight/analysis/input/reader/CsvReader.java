package com.acme.flight.analysis.input.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.csveed.api.CsvClient;
import org.csveed.api.CsvClientImpl;
import org.csveed.bean.ColumnNameMapper;

import com.acme.flight.analysis.model.ArrivalInfo;

public class CsvReader implements Reader {

    private CsvClient<ArrivalInfo> csvClient;

	public CsvReader(String path) throws FileNotFoundException {
		csvClient = new CsvClientImpl<ArrivalInfo>(new BufferedReader(new FileReader(new File(path))), ArrivalInfo.class).setMapper(ColumnNameMapper.class);
	}
	
	public CsvReader(InputStream inputStream) {
		csvClient = new CsvClientImpl<ArrivalInfo>(new BufferedReader(new InputStreamReader(inputStream)), ArrivalInfo.class).setMapper(ColumnNameMapper.class);
	}
	
	@Override
	public ArrivalInfo read() {
		if(!csvClient.isFinished()) {
			ArrivalInfo arrivalInfo = csvClient.readBean();
			// TODO this check should not be required.
			// For some unknown reason the library is returning null for the non-existant line after the last line.
			// This condition guards against that issue
			if(arrivalInfo != null) {
				arrivalInfo.setArrivalId(csvClient.getCurrentLine());
				// TODO : Temp hack for now till I identify a better mechanism to use csveed library to be able to read properties into associations
				arrivalInfo.initFlight();
				return arrivalInfo;
			}
		}
		
		return null;
	}

}
