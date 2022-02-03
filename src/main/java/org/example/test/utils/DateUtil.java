package org.example.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
public class DateUtil {

	@Autowired
	private StringUtil stringUtil;

	public String getSimpleDateFormatInBn(Date date) {
		if(date != null){
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return stringUtil.convertToBn(simpleDateFormat.format(date));
		} else {
			return StringUtils.EMPTY;
		}

	}

	public String getSimpleDateFormatInBn(LocalDate date) {
		if(date != null){
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return stringUtil.convertToBn(dateTimeFormatter.format(date));
		} else {
			return StringUtils.EMPTY;
		}

	}
}
