package kr.or.ddit.servlet08;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*; // 캘린더가 가지고있는 모든 static은 여기에 있다.

import java.text.DateFormatSymbols;

public class CalendarWrapper {
	private Calendar adaptee;
	private Locale locale;
	
	private int offset;
	private int dayOfWeekFirst;
	private int lastDate;
	private int beforeYear;
	private int beforeMonth;
	private int currentYear;
	private int currentMonth;
	private int nextYear;
	private int nextMonth;
	
	
	private String[] weekDays;
	private String[] months;
	
	public CalendarWrapper(Calendar adaptee, Locale locale) {
		super();
		this.adaptee = adaptee;
		this.locale = locale;

		DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale); // locale를 클라이언트한테 받은..
		weekDays = dfs.getWeekdays();
		months = dfs.getMonths();

//		adaptee.set(Calendar.DAY_OF_MONTH, 1); // 12월 1일로 set한다.
		adaptee.set(DAY_OF_MONTH, 1); // mport static java.util.Calendar.*;로 설정해서 Calendar를 생략 가능 : DAY_OF_MONTH는 현재 월
		dayOfWeekFirst = adaptee.get(DAY_OF_WEEK); // 1일이 무슨 요일인지에 대한 값
		offset = dayOfWeekFirst - SUNDAY; // 일요일에서 뺀 값
		lastDate = adaptee.getActualMaximum(DAY_OF_MONTH); // 현재 월의 마지막 날짜		
		
		currentYear = adaptee.get(YEAR);
		currentMonth = adaptee.get(MONTH);
		
		adaptee.add(MONTH, -1); // 현재 날짜에서 한달을 뺀다. 
		beforeYear = adaptee.get(YEAR); // 이렇게 만들고 값만 넣어주고 맨 밑에 다시 원래 날짜로 만들거다.
		beforeMonth = adaptee.get(MONTH);
		
		adaptee.add(MONTH, 2); // 위에서 -1를 했기때문에 여기선 +2
		nextYear = adaptee.get(YEAR);
		nextMonth = adaptee.get(MONTH);
		
		adaptee.add(MONTH, -1); // 다시 현재 월로 만들어줘야 한다.
		
	}

	
	public Calendar getAdaptee() {
		return adaptee;
	}

	public void setAdaptee(Calendar adaptee) {
		this.adaptee = adaptee;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getDayOfWeekFirst() {
		return dayOfWeekFirst;
	}

	public void setDayOfWeekFirst(int dayOfWeekFirst) {
		this.dayOfWeekFirst = dayOfWeekFirst;
	}
	
	public int getLastDate() {
		return lastDate;
	}

	public void setLastDate(int lastDate) {
		this.lastDate = lastDate;
	}
	
	public String[] getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String[] weekDays) {
		this.weekDays = weekDays;
	}
	
	public int getBeforeYear() {
		return beforeYear;
	}


	public void setBeforeYear(int beforeYear) {
		this.beforeYear = beforeYear;
	}


	public int getBeforeMonth() {
		return beforeMonth;
	}


	public void setBeforeMonth(int beforeMonth) {
		this.beforeMonth = beforeMonth;
	}


	public int getNextYear() {
		return nextYear;
	}


	public void setNextYear(int nextYear) {
		this.nextYear = nextYear;
	}


	public int getNextMonth() {
		return nextMonth;
	}


	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}
	
	public int getCurrentYear() {
		return currentYear;
	}


	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}


	public int getCurrentMonth() {
		return currentMonth;
	}


	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	


	public Locale getLocale() {
		return locale;
	}


	public void setLocale(Locale locale) {
		this.locale = locale;
	}


	@Override
	public String toString() {
//		return String.format("%1$tY, %1$tB", adaptee); // 1번째 아규먼트 인덱스를 뜻한다.
		return String.format(locale, "%1$tY, %1$tB", adaptee); // locale를 넣어줄수 있다.
		
	}
	
}
