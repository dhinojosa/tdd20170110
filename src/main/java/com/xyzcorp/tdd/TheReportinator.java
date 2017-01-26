package com.xyzcorp.tdd;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TheReportinator {

	private List<CheckoutRecord> asList;
	private Supplier<LocalDate> supplier;

	public TheReportinator(List<CheckoutRecord> asList, Supplier<LocalDate> supplier) {
		this.asList = asList;
		this.supplier = supplier;
	}

	public List<CheckoutRecord> reportViolators(int count) {
		if (count < 0) throw new IllegalArgumentException("Limit cannot be negative");
		return asList.stream()
				   .filter(cr -> cr.calculatePenalty(supplier.get()) > 0)
				   .sorted(Comparator.comparing(CheckoutRecord::getDate))
				   .limit(count)
				   .collect(Collectors.toList());

	}

	public List<String> reportViolatorsAsString(int i) {
		return reportViolators(i)
				.stream()
				.map(c -> String.format("%s : %d", c.getName(), c.calculatePenalty(supplier.get())))
				.collect(Collectors.toList());
	}


}
