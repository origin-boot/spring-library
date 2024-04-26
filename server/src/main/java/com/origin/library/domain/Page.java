package com.origin.library.domain;

import java.util.List;

public class Page<T> {
	public int total;
	public List<T> records;

	public Page(List<T> records, int total) {
		this.total = total;
		this.records = records;
	}
}
