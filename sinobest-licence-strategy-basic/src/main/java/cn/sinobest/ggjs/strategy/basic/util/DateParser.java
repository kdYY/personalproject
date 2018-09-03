package cn.sinobest.ggjs.strategy.basic.util;

import java.util.*;
import java.text.*;

public class DateParser
{
    private Calendar a;
    
    public DateParser(final Date time) {
        (this.a = Calendar.getInstance()).setTime(time);
    }
    
    public DateParser(final String s) {
        this(toUtilDate(s));
    }
    
    public static Date toUtilDate(final String s) {
        Date date = null;
        try {
            date = new SimpleDateFormat(a("Zm>=\u0013nYj Z")).parse(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                date = new SimpleDateFormat(a("nYh Z\fm>=G")).parse(s);
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        return date;
    }
    
    public static java.sql.Date toSqlDate(final Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
    
    public static java.sql.Date toSqlDate(final String s) {
        return toSqlDate(toUtilDate(s));
    }
    
    public String getYear() {
        return Integer.toString(this.a.get(1));
    }
    
    public String getXXYear() {
        return this.getYear().substring(2, 4);
    }
    
    public int getMonth() {
        return this.a.get(2) + 1;
    }
    
    public int getDayOfMonth() {
        return this.a.get(5);
    }
    
    public int getDayOfWeek() {
        return this.a.get(7);
    }
    
    public static void main(final String[] array) {
        final int b = FileUtil.b;
        final DateParser dateParser = new DateParser(array[0]);
        System.out.println(a("Zq&6\u0003") + dateParser.getYear());
        System.out.println(a("Zq&6\f\u001e") + dateParser.getXXYear());
        System.out.println(a("N{)0V\u001e") + dateParser.getMonth());
        System.out.println(a("Gu>y") + dateParser.getDayOfMonth());
        System.out.println(a("Tq\"/\u0003") + dateParser.getDayOfWeek());
    }
    
    private static String a(final String s){
		final char [] chars = s.toCharArray();
		final char [] fiveChars = new char[]{'#','\u0014','G','D','>'};
		for(int i =0 ; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ fiveChars[i%5]);
		}
        return new String(chars);
	}
}
