package com.aim.game_base.net;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * byCodeGame 工具
 * 
 * @author xjd
 *
 */
public class Utils {
	private final static Random random = new Random();
	private final static String partitionStr = "___";
	private static final DateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
	private static final String yearFormat = "yyyy-MM-dd";
	private static final DateFormat dateFormat = new SimpleDateFormat(yearFormat);
	private static final String REGEX = "^[a-zA-Z0-9]++$";

	/**
	 * 获取随机数 栗子：startIndex =0,base = 2<br>
	 * 返回可能是0,1,2
	 * 
	 * @param startIndex
	 * @param base
	 * @return
	 */
	public static final int getRandomNum(int startIndex, int base) {
		if (base <= 0) {
			return 0;
		}
		return startIndex + random.nextInt(base - startIndex + 1);
	}

	public static final int getRandomNum(int limit) {
		if (limit <= 0) {
			return 0;
		}
		return random.nextInt(limit);
	}

	public static final String getDataStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	/**
	 * 未使用
	 * 
	 * @param time
	 * @return
	 */
	public static byte getServerSeason(int time) {
		return (byte) (((Utils.getNowIntTime() - time) / 86400) % 4);
	}

	/**
	 * 随机生成指定长度的字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String randowStr(int num) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) { // ��base��������16Ϊ����ַ�
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static final boolean isHitByPercent(int seed) {
		int x = random.nextInt(100);
		if (x < seed) {
			return true;
		}

		return false;
	}

	public static int getHoursValue() {
		return hours;
	}

	private static int currentHours = 0;

	public static int getCurrentHours() {
		return currentHours;
	}

	public static void setCurrentHours() {
		Calendar calendar = Calendar.getInstance();
		currentHours = calendar.get(Calendar.HOUR_OF_DAY);
	}

	private static int hours = 0;

	public static void setOverrallHoursValue() {
		String s = format.format(new Date());
		long result = Long.parseLong(s);

		hours = (int) (result / 10000);
	}

	/**
	 * 获取日志记录字符
	 * 
	 * @param args
	 * @return
	 */
	public static String getLogString(Object... args) {
		StringBuilder sb = new StringBuilder();
		for (Object arg : args) {
			String value = String.valueOf(arg);
			sb.append(value).append(partitionStr);
		}
		return sb.toString();
	}

	/**
	 * 获取当前月份天数
	 * 
	 * @return
	 */
	public static int getMDays() {
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int day = aCalendar.getActualMaximum(Calendar.DATE);
		return day;
	}

	/**
	 * 获取当前天数
	 * 
	 * @return
	 */
	public static int getDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		return day;
	}

	public static byte getNowMonth() {
		Calendar cal = Calendar.getInstance();
		byte month = (byte) (cal.get(Calendar.MONTH) + 1);
		return month;
	}

	/**
	 * 获取当前时间距离下个小时相差的秒数
	 * 
	 * @return
	 */
	public static short getNextHourDiffSecond() {
		Calendar now = Calendar.getInstance();
		int nowMinute = now.get(Calendar.MINUTE);
		int nowSecond = now.get(Calendar.SECOND);

		int minute = 60 - nowMinute;
		int result = minute * 60 - nowSecond;
		return (short) result;
	}

	/**
	 * 检测两个时间相差天数是否超过一天(包括一 天),是,返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean getDayDifference(long date1, long date2) {
		Calendar first = Calendar.getInstance();
		first.setTimeInMillis(date1);
		Calendar second = Calendar.getInstance();
		second.setTimeInMillis(date2);
		int firstDay = first.get(Calendar.DAY_OF_MONTH);
		int firstMonth = first.get(Calendar.MONTH);
		int firstYear = first.get(Calendar.YEAR);

		int secondDay = second.get(Calendar.DAY_OF_MONTH);
		int secondMonth = second.get(Calendar.MONTH);
		int secondYear = second.get(Calendar.YEAR);

		if (Utils.getAbs(secondYear - firstYear) > 1) {
			return true;
		}

		if (Utils.getAbs(secondMonth - firstMonth) > 1) {
			return true;
		}

		if (Utils.getAbs(secondDay - firstDay) >= 1) {
			return true;
		}

		return false;
	}

	//
	// public static boolean getTenMiniteDifference(long date1, long date2) {
	// long diff = date2 - date1;
	// long second = RankingConstants.GET_RANK_TIME * 1000;
	// if (diff >= second) {
	// return true;
	// }
	// return false;
	// }

	/**
	 * 获取一个数的绝对值
	 * 
	 * @param x
	 * @return
	 */
	public static int getAbs(int x) {
		int y = x >> 31;
		return (x + y) ^ y;
	}

	public static final int getDate() {
		return today;
	}

	private static int today = 0;

	public static final void setDate() {
		Calendar calendar = Calendar.getInstance();
		today = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100
				+ calendar.get(Calendar.DAY_OF_MONTH);

		int month = (today % 10000) / 100;
		if (month == 12) {
			nextMonthDay = (calendar.get(Calendar.YEAR) + 1) * 10000 + 100 + calendar.get(Calendar.DAY_OF_MONTH);
		} else {
			nextMonthDay = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 2) * 100
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}

		if (month == 1) {
			prevMonthDay = (calendar.get(Calendar.YEAR) - 1) * 10000 + 1200 + calendar.get(Calendar.DAY_OF_MONTH);
		} else {
			prevMonthDay = calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH)) * 100
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}

		month = calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH);
	}

	private static int nextMonthDay = 0;

	private static int prevMonthDay = 0;

	public static final int getPrevMonthDay() {
		return prevMonthDay;
	}

	public static final int getNextMonthDay() {
		return nextMonthDay;
	}

	private static int month = 0;

	public static int getMonth() {
		return month;
	}

	/**
	 * 判断闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeap(int year) {
		if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
			return true;
		else
			return false;
	}

	/**
	 * 返回当月天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDays(int year, int month) {
		int days;
		int FebDay = 28;
		if (isLeap(year))
			FebDay = 29;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			days = FebDay;
			break;
		default:
			days = 0;
			break;
		}
		return days;
	}

	/**
	 * 检测当天是不是本月最后的周六或者周天
	 * 
	 * @return
	 */
	public static boolean checkSundays() {
		// 当前月数所有的周六周天
		List<Integer> sundays = new ArrayList<Integer>();
		Calendar setDate = Calendar.getInstance();
		int year = setDate.get(Calendar.YEAR);
		int month = setDate.get(Calendar.MONTH);
		// 从第一天开始
		int day;
		for (day = 1; day <= getDays(year, month); day++) {
			setDate.set(Calendar.DATE, day);
			int firstMonth = setDate.get(Calendar.DAY_OF_WEEK);
			if (firstMonth == 1) {
				sundays.add(day);
			}

			if (firstMonth == 7) {
				sundays.add(day);
			}
		}
		boolean result = false;
		int today = setDate.get(Calendar.DAY_OF_MONTH);
		int size = sundays.size();
		if (sundays.get(size - 1) == today) {
			result = true;
		}
		if (sundays.get(size - 2) == today) {
			result = true;
		}
		return result;
	}

	/**
	 * 检测是否处于双倍阅历奖励时间
	 * 
	 * @return
	 */
	public static boolean checkDoubleSophisticateTime() {
		// 12：00-14:00 周二和周四
		Calendar setDate = Calendar.getInstance();
		boolean result = false;
		int week = setDate.get(Calendar.DAY_OF_WEEK);
		if (week == 3 || week == 5) {
			int hour = setDate.get(Calendar.HOUR_OF_DAY);
			if (hour == 12 || hour == 13) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText 要加密的内容
	 * @param algorithmName 加密算法名称：md5或者sha-1,不区分大小写
	 * @return
	 */
	public static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	// /***
	// * 将配置表的字符串转换成奖励Set
	// * @param value
	// * @return
	// * @author xjd
	// */
	// public static Set<ChapterReward> changStrToAward(String value)
	// {
	// Set<ChapterReward> set = new HashSet<ChapterReward>();
	// if(value != null && !value.equals("0;")){
	// String[] strs = value.split(";");
	// for(String str : strs){
	// String[] vStr = str.split(",");
	// ChapterReward chapterReward = new ChapterReward();
	// chapterReward.setType(Byte.valueOf(vStr[0]));
	// chapterReward.setItemId(Integer.parseInt(vStr[1]));
	// chapterReward.setNum(Integer.parseInt(vStr[2]));
	// set.add(chapterReward);
	// }
	// }
	// return set;
	// }

	/** 根据当前时间获取距离第二天中午12点的时间 差值 */
	public static long getNextDayTwelveTime() {
		long ll = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		ll = cal.getTimeInMillis() - System.currentTimeMillis();
		return ll;
	}

	/**
	 * 返回十六进制字符串 </br> encrypt方法调用
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	/**
	 * 获取一个以Integer类型为Key的map中
	 * 
	 * @return key的最大值
	 * @param map
	 */
	public static int getMaxKeyByMap(Map<Integer, Integer> map) {
		int index = 0;
		for (Integer indexs : map.keySet()) {
			if (index < indexs) {
				index = indexs;
			}
		}
		return index;
	}

	/**
	 * 检测账号名称合法性
	 * 
	 * @return
	 */
	public static boolean checkCountFormat(String count) {
		boolean flag = false;
		if (count.matches(REGEX)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 获取当前系统时间 秒数
	 * 
	 * @return
	 */
	public static int getNowIntTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static long getNowLongTime(){
		return System.currentTimeMillis();
	}

	/**
	 * 获取一个UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static int getCityPathNum(int currentCityId, int targetCityId) {
		int min = 0;
		int max = 0;
		if (currentCityId < targetCityId) {
			min = currentCityId;
			max = targetCityId;
		} else {
			min = targetCityId;
			max = currentCityId;
		}

		int pathNum = Integer.parseInt(min + "" + max);
		return pathNum;
	}

	/**
	 * 获得剩余时间的通用方法
	 * 
	 * @param nowTime 获得现在时间
	 * @param startTime 获得开始时间
	 * @param cdTime 获得cd时间
	 * @return
	 * @author wcy 2016年1月20日
	 */
	public static int getRemainTime(int nowTime, int startTime, int cdTime) {
		int endTime = startTime + cdTime;
		int remainTime = endTime - nowTime;
		if (remainTime > 0) {
			return remainTime;
		}
		return 0;
	}

	/**
	 * 胜利失败的字
	 * 
	 * @param type
	 * @return
	 * @author wcy 2016年2月19日
	 */
	public static String getFightResultName(int type) {
		return type == 1 ? "胜利" : "失败";
	}

	/*** 数组转换成Str */
	public static final int[] CARD_ID = { 16101, 16102, 16103, 16104, 16105, 16106, 16107, 16108, 16201, 16202, 16203,
			16204, 16205, 16206, 16207, 16208, 16209, 16210, 16301, 16302, 16303, 16304, 16305 };

	public static String randomCard() {
		String[] sequence = new String[15];
		Random random = new Random();

		for (int i = 0; i < sequence.length; i++) {
			int index = random.nextInt(CARD_ID.length);
			sequence[i] = CARD_ID[index] + "";
		}

		return join(sequence, ",");
	}

	public static String join(Object[] str, String separator) {
		StringBuilder sb = new StringBuilder();
		if (str.length == 0) {
			return sb.toString();
		}
		for (int i = 0; i < str.length - 1; i++) {
			sb.append(str[i]).append(separator);
		}
		sb.append(str[str.length - 1]);
		return sb.toString();
	}

	/**
	 * 获得今天的固定秒时间
	 * 
	 * @param nowTime
	 * @return
	 * @author wcy 2016年6月7日
	 */
	public static int getTodayTimePoint(int nowTime, int hour, int minutes) {
		int result = nowTime / (24 * 60 * 60) * (24 * 60 * 60);
		int offsetHour = Calendar.getInstance(Locale.CHINA).getTimeZone().getRawOffset() / (3600 * 1000);
		result += hour * 60 * 60 - offsetHour * 60 * 60;
		result += minutes * 60;
		return result;
	}

	/**
	 * 获得今天的固定秒时间,timeStr必须是符合format格式的字符串
	 * 
	 * @param nowTime
	 * @param timeStr
	 * @param format
	 * @return
	 * @author wcy 2016年8月29日
	 */
	public static int getTodayTimePointByRegex(int nowTime, String timeStr, String format) {
		long time = (long) nowTime * 1000;
		Date d = new Date(time);
		String regex = yearFormat + format;

		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		String yearStr = dateFormat.format(d);
		try {
			d = sdf.parse(yearStr + timeStr);
			return (int) (d.getTime() / 1000);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 返回相距的秒数 HH:mm:ss
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author wcy 2016年7月26日
	 */
	public static int diffTime(String startTime, String endTime) {
		String pattern = "HH:mm:ss";

		SimpleDateFormat time1 = new SimpleDateFormat(pattern);
		SimpleDateFormat time2 = new SimpleDateFormat(pattern);
		try {
			Date date1 = time1.parse(startTime);
			Date date2 = time2.parse(endTime);
			int diff = (int) (date2.getTime() - date1.getTime()) / 1000;
			return diff;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 返回当前时间到指定时间的秒数 HH:mm:ss
	 * 
	 * @param endTime
	 * @return
	 * @author wcy 2016年7月26日
	 */
	public static int diffTime(String endTime) {
		String pattern = "HH:mm:ss";
		SimpleDateFormat time2 = new SimpleDateFormat(pattern);
		SimpleDateFormat nowTimeFormat = new SimpleDateFormat(pattern);
		SimpleDateFormat time1 = new SimpleDateFormat(pattern);
		try {
			Date nowDate = new Date(System.currentTimeMillis());
			String nowTime = nowTimeFormat.format(nowDate);
			Date date1 = time1.parse(nowTime);
			Date date2 = time2.parse(endTime);
			int diff = (int) (date2.getTime() - date1.getTime()) / 1000;

			return diff;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
