import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Day implements Cloneable{
	
	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	public Day(String sDay) {
		set(sDay);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copy;
	}
	
	public void set(String sDay) {
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]);
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1])/3+1;
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	public static boolean valid(int y, String month, int d)
	{
		int m=0;;
		final String[] Month = {
		"Jan", "Feb", "Mar", "Apr",
		"May", "Jun", "Jul", "Aug",
		"Sep", "Oct", "Nov", "Dec"};
		for(int i=0; i <Month.length; i++) {
			if(month.equals(Month[i])) {
				m=i+1;
				break;
			}
		}
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	@Override
	public String toString() {
		//final String[] Month = {
//				"Jan", "Feb", "Mar", "Apr",
//				"May", "Jun", "Jul", "Aug",
//				"Sep", "Oct", "Nov", "Dec"};
		
		//return day+" "+ Month[month-1] + " "+ year;
		return day+"-"+ MonthNames.substring((month-1)*3, (month)*3) + "-"+ year;
	}
	
	public int monthStringToInt(String month) {
		int m=0;;
		final String[] Month = {
		"Jan", "Feb", "Mar", "Apr",
		"May", "Jun", "Jul", "Aug",
		"Sep", "Oct", "Nov", "Dec"};
		for(int i=0; i <Month.length; i++) {
			if(month.equals(Month[i])) {
				m=i+1;
				break;
			}
		}
		return m;
		
	}

	public boolean isPreviousDate(int y, String mon, int d) {
		int m=monthStringToInt(mon);
		
		if(year>y || this.month > m && year>=y || day> d && this.month >= m && year>=y) {
			return true;
		}
		return false;
	}
}