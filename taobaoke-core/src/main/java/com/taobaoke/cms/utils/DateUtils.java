package com.taobaoke.cms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	public static Date parseDate(String dateStr, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);

		Date date = null;
		if (StringUtils.isEmpty(dateStr)) {
			return date;
		}

		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date currentDayStartTime() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		return cl.getTime();
	}

	public static Date currentDayEndTime() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.HOUR_OF_DAY, 23);
		cl.set(Calendar.MINUTE, 59);
		cl.set(Calendar.SECOND, 59);
		return cl.getTime();
	}

	public static Date suchDayStartTime(Date date) {
		Calendar cl = getCalendar(date);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		return cl.getTime();
	}

	public static Date suchDayEndTime(Date date) {
		Calendar cl = getCalendar(date);
		cl.set(Calendar.HOUR_OF_DAY, 23);
		cl.set(Calendar.MINUTE, 59);
		cl.set(Calendar.SECOND, 59);
		return cl.getTime();
	}

	public static Date parseDate(String dateStr, String format,
			String defaultStr) {
		Date date = parseDate(dateStr, format);
		if (date == null) {
			date = parseDate(defaultStr, format);
		}
		return date;
	}

	public static Date parseDate(String dateStr, String format, Date defaultDate) {
		Date date = parseDate(dateStr, format);
		if (date == null) {
			date = defaultDate;
		}
		return date;
	}

	public static String changeDate2Str(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		if (dateFormat == null || date == null) {
			return "";
		}

		return dateFormat.format(date);
	}

	public static int getYear(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.MONTH);
	}

	public static int getDay(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.HOUR_OF_DAY);
	}

	private static Calendar getCalendar(Date date) {
		Calendar cl = Calendar.getInstance();
		if (date != null) {
			cl.setTime(date);
		}
		return cl;
	}

	public static int getMinute(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.SECOND);
	}

	public static Date addSecond(Date date, int addSecAmount) {
		Calendar cl = getCalendar(date);
		cl.add(Calendar.SECOND, addSecAmount);
		return cl.getTime();
	}
	
	public static Date addDay(Date date, int addSecAmount) {
		Calendar cl = getCalendar(date);
		cl.add(Calendar.DAY_OF_YEAR, addSecAmount);
		return cl.getTime();
	}

	public static Date currentWeekSunday() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_WEEK, 7);
		cl.add(Calendar.DAY_OF_YEAR, 1);
		cl.set(Calendar.HOUR_OF_DAY, 23);
		cl.set(Calendar.MINUTE, 59);
		cl.set(Calendar.SECOND, 59);
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTime();
	}

	public static Date currentWeekMonday() {
		Calendar cl = Calendar.getInstance();
		if (cl.get(Calendar.DAY_OF_WEEK) == 1) {
			cl.add(Calendar.DAY_OF_MONTH, -1);
		}
		cl.set(Calendar.DAY_OF_WEEK, 2);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		// cl.add(Calendar.DAY_OF_YEAR, 1);
		return cl.getTime();
	}

	public static String getTimeGroup(Date date, int minute, String format) {
		Date newDate = getTimeGroup(date, minute);
		return changeDate2Str(newDate, format);
	}

	public static Date getTimeGroup(Date date, int minute) {
		Calendar cl = getCalendar(date);
		int givenMinute = cl.get(Calendar.MINUTE);
		if (givenMinute < minute) {
			cl.set(Calendar.MINUTE, 0);
			return cl.getTime();
		}
		cl.set(Calendar.MINUTE, minute);

		return cl.getTime();
	}

	public static void main(String[] args) throws ParseException {
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.HOUR, 1);

		Calendar cl2 = Calendar.getInstance();
		System.out.println((cl.getTime().getTime() - cl2.getTime().getTime())
				/ (1000 * 60 * 30));
	}
}
