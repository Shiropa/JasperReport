package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class HelperService {

	public String getSimpleDateFormatInBn(Date date) {
		if(date != null){
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return toBnStr(simpleDateFormat.format(date));
		} else {
			return StringUtils.EMPTY;
		}

	}

	public String getSimpleDateFormatInBn(LocalDate date) {
		if(date != null){
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return toBnStr(dateTimeFormatter.format(date));
		} else {
			return StringUtils.EMPTY;
		}

	}

	public String toBnStr(String s) {
		return s
				.replaceAll("0", "০")
				.replaceAll("1", "১")
				.replaceAll("2", "২")
				.replaceAll("3", "৩")
				.replaceAll("4", "৪")
				.replaceAll("5", "৫")
				.replaceAll("6", "৬")
				.replaceAll("7", "৭")
				.replaceAll("8", "৮")
				.replaceAll("9", "৯");
	}
}
