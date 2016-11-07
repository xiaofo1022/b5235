package com.xiaofo1022.b5235.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy年MM月dd日");
  
  private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
  
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
  
  public static String getDay(Date date) {
    if (date != null) {
      return dayFormat.format(date) + " 周" + getDayofWeek(date);
    }
    return "";
  }
  
  public static String getDayofWeek(Date date) {
    if (date != null) {
      calendar.setTime(date);
      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
      switch (dayOfWeek) {
        case 1:
          return "日";
        case 2:
          return "一";
        case 3:
          return "二";
        case 4:
          return "三";
        case 5:
          return "四";
        case 6:
          return "五";
        case 7:
          return "六";
        default:
          break;
      }
    }
    return "";
  }
  
  public static String getTimeInDay(Date date) {
    if (date != null) {
      String timeLabel = "";
      calendar.setTime(date);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      if (hour > 0 && hour <= 5) {
        timeLabel += "凌晨";
      } else if (hour > 5&& hour <= 8) {
        timeLabel += "早上";
      } else if (hour > 8 && hour <= 11) {
        timeLabel += "上午";
      } else if (hour > 11 && hour <= 13) {
        timeLabel += "中午";
      } else if (hour > 13 && hour <= 18) {
        timeLabel += "下午";
      } else if (hour > 18) {
        timeLabel += "晚上";
      }
      int midHour = calendar.get(Calendar.HOUR);
      if (midHour == 0) {
        midHour = 12;
      }
      timeLabel += midHour + "点" + calendar.get(Calendar.MINUTE) + "分";
      return timeLabel;
    }
    return "";
  }
}
