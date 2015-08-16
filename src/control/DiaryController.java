package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class DiaryController
{
	public DiaryController()
	{

	}

	public void createDiary(String inputStr, String dir, String date)
	{
		//�����������ռ��ļ�
		File file = new File(dir + "\\" + date + ".txt");
		FileWriter outputFile;
		try
		{
			outputFile = new FileWriter(file);
			outputFile.write(inputStr);
			outputFile.flush();
			outputFile.close();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getDiary(String dir, String date)
	{
		//�򿪲���ȡ�ռ��ļ�
		String content = "";
		try
		{
			String url = dir + "\\" + date + ".txt";
//			String dir = "C:\\Users\\xuzywozz\\Desktop\\Project\\MyDiary\\"
//					+ date + ".txt";
			File file = new File(url);
			if (file.isFile() && file.exists())
			{ // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					content += lineTxt + '\n';
				}
				read.close();
			}
			else
			{
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		}
		catch (Exception e)
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return content;
	}

	public static void main(String[] args)
	{
		// test
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println("����ʱ���ǣ�" + new Date());
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		System.out.println("����ʱ���ǣ�" + year + "��" + month + "��" + day + "�գ�����"
				+ week);
		calendar.add(Calendar.DAY_OF_MONTH, -20);
		year = String.valueOf(calendar.get(Calendar.YEAR));
		month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		System.out.println("����ʱ���ǣ�" + year + "��" + month + "��" + day + "�գ�����"
				+ week);
	}

}
