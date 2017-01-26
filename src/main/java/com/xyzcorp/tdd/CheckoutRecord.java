package com.xyzcorp.tdd;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.MONTHS;
public class CheckoutRecord {

	private String name;
	private String title;
	private LocalDate date;

	public CheckoutRecord(String name, String title, LocalDate date) {
        this.name = name;
        this.title = title;
        this.date = date;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getDate() {
		return date;
	}

	public int calculatePenalty(LocalDate now) {
		if (now.isBefore(date)) return 0;
		return (int) MONTHS.between(date, now) * 10;
	}
}
