package com.xiaofo1022.b5235.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public static Date getOneDayStart(Date date) {
    String day = dateFormat.format(date);
    try {
      return timeFormat.parse(day + " 00:00:00");
    } catch (ParseException e) {
    }
    return date;
  }
  
  public static Date getOneDayEnd(Date date) {
    String day = dateFormat.format(date);
    try {
      return timeFormat.parse(day + " 23:59:59");
    } catch (ParseException e) {
    }
    return date;
  }
}
