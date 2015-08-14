package control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateController
{
	
	public String getTime(Calendar calendar)
	{
		//以指定格式输出当前日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		return df.format(calendar.getTime());// new Date()为获取当前系统时间
	}
	
	public Calendar initializeCalendar()
	{
		//初始化calendar
		Calendar calendar = Calendar.getInstance();
		return calendar;
	}
	
	public Calendar getThisDate(Calendar calendar)
	{
		//获取当天的calendar
		calendar.setTime(new Date());
		return calendar;
	}
	
	public Calendar getLastDate(Calendar calendar)
	{
		//获取前一天的calendar
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}
	
	public Calendar getNextDate(Calendar calendar)
	{
		//获取后一天的calendar
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public static void main(String[] args)
	{
		//test
		DateController dc = new DateController();
		Calendar calendar = dc.initializeCalendar();
		System.out.println("当前日期：" + dc.getTime(calendar));
		System.out.println("前一天：" + dc.getTime(dc.getLastDate(calendar)));
		System.out.println("后一天：" + dc.getTime(dc.getNextDate(calendar)));
	}

}
