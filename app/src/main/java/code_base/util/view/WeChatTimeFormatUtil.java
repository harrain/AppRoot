package code_base.util.view;

import android.text.TextUtils;
import android.util.Log;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by net on 2018/3/12.
 * 仿微信消息页显示格式化后的时间
 */

public class WeChatTimeFormatUtil {
    private static final String tag = "WeChatTimeFormatUtil";
    /**
     * 按微信时间表示规则格式化时间
     * @param date 现在的日期
     * @param time 要格式化的时间
     */
    public static String handleTime(String time, Date date, SimpleDateFormat sdf) {
        if (TextUtils.isEmpty(time) || date == null || sdf == null) return "";


        Date start = null;
        try {
            start = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(start);
        DateTime nowDT = new DateTime(date);
        assert start != null;
        if (start.getTime() > date.getTime()) {//时间参数不能超过现在时间
            Log.i(tag, "handleTime: "+"时间有误，不是过去时间，超过了此时此刻");
            return "";
        }

        if (dateTime.getYearOfCentury() == nowDT.getYearOfCentury()) {//在近一年内的情况
            int days = nowDT.getDayOfYear() - dateTime.getDayOfYear();

            if (days < 2) {
                if (days == 1) {
                    String result = "昨天";
                    return result;
                } else {
                    String hm = time.substring(13, 16);
                    int hour = dateTime.getHourOfDay();

                    if (hour == 12) {
                        String result = "中午" + hm;
                        return result;
                    } else if (hour < 12) {
                        String result = "早上" + hour + hm;
                        return result;
                    } else if (hour > 12) {
                        hour = hour - 12;
                        String result = "下午" + hour + hm;
                        return result;
                    }
                }
            } else {//周的情况
                int week = dateTime.getWeekOfWeekyear();
                int nowWeek = nowDT.getWeekOfWeekyear();
                if (week == nowWeek) {
                    String result = dateTime.dayOfWeek().getAsShortText(Locale.CHINESE);
                    return result;
                } else if (nowWeek - week == 1) {
                    if (nowDT.dayOfWeek().getAsShortText(Locale.ENGLISH).equals("Mon") || nowDT.dayOfWeek().getAsShortText(Locale.ENGLISH).equals("Tue")) {
                        String result = dateTime.dayOfWeek().getAsShortText(Locale.CHINESE);
                        return result;
                    }
                } else {
                    int month = dateTime.getMonthOfYear();
                    int day = dateTime.getDayOfMonth();
                    String result = month + "月" + day + "日";
                    return result;
                }
            }

        } else {//不在同一年的情况
            int year = dateTime.getYear();//2017XX   getYearOfCentury只是超过2000年的数字
            int month = dateTime.getMonthOfYear();
            int day = dateTime.getDayOfMonth();
            String result = year + "年" + month + "月" + day + "日";
            return result;
        }
        return "";

    }
}
