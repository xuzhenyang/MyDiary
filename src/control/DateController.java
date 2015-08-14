package control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateController
{
	
	public String getTime(Calendar calendar)
	{
		//��ָ����ʽ�����ǰ����
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
		return df.format(calendar.getTime());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	
	public Calendar initializeCalendar()
	{
		//��ʼ��calendar
		Calendar calendar = Calendar.getInstance();
		return calendar;
	}
	
	public Calendar getThisDate(Calendar calendar)
	{
		//��ȡ�����calendar
		calendar.setTime(new Date());
		return calendar;
	}
	
	public Calendar getLastDate(Calendar calendar)
	{
		//��ȡǰһ���calendar
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}
	
	public Calendar getNextDate(Calendar calendar)
	{
		//��ȡ��һ���calendar
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public static void main(String[] args)
	{
		//test
		DateController dc = new DateController();
		Calendar calendar = dc.initializeCalendar();
		System.out.println("��ǰ���ڣ�" + dc.getTime(calendar));
		System.out.println("ǰһ�죺" + dc.getTime(dc.getLastDate(calendar)));
		System.out.println("��һ�죺" + dc.getTime(dc.getNextDate(calendar)));
	}

}
