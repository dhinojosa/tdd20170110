package com.xyzcorp.tdd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class LibraryRunner {
    public static void main(String[] args) {
    	    InputStream is = LibraryRunner.class.getResourceAsStream("/library.txt");
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(reader);
		
		TheParsenator theParsenator = new TheParsenator("~");
		List<CheckoutRecord> records = theParsenator.getCheckoutRecords(buff.lines());
    	
		TheReportinator theReportinator = new TheReportinator(records, () -> LocalDate.now());
		
		for (String item : theReportinator.reportViolatorsAsString(5)) {
			System.out.println(item);
		}
    }
}
