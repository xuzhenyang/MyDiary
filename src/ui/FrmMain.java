package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import control.DateController;
import control.DiaryController;
import control.TextProcess;

import javax.swing.JLabel;

import java.awt.Toolkit;

public class FrmMain implements ActionListener
{

	private JFrame frmDiary;
	private JButton btnSave;
	private JTextPane textPane;
	private JButton btnLast;
	private JButton btnNext;
	private JLabel lblToday;
	private JMenuItem mnSetting;
	private static Calendar calendar = (new DateController())
			.initializeCalendar();
	private DiaryController diaryController = new DiaryController();
	private DateController dateController = new DateController();
	private String date = dateController.getTime(calendar);
	//日记存放路径
	public static String dir = "H:\\MyDiary";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					FrmMain window = new FrmMain();
					window.frmDiary.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmMain()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmDiary = new JFrame();
		frmDiary.setTitle("Diary");
		frmDiary.setBounds(100, 100, 450, 300);
		frmDiary.setLocationRelativeTo(null);// 使窗体居中显示
		frmDiary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textPane = new JTextPane();
		read(date);
		frmDiary.getContentPane().add(textPane, BorderLayout.CENTER);

		btnSave = new JButton("Save");
		btnSave.setBorder(BorderFactory.createEtchedBorder());// 蚀刻样式
		frmDiary.getContentPane().add(btnSave, BorderLayout.SOUTH);
		btnSave.addActionListener(this);

		btnLast = new JButton("Last");
		btnLast.setBorder(BorderFactory.createEtchedBorder());
		frmDiary.getContentPane().add(btnLast, BorderLayout.WEST);
		btnLast.addActionListener(this);

		btnNext = new JButton("Next");
		btnNext.setBorder(BorderFactory.createEtchedBorder());
		frmDiary.getContentPane().add(btnNext, BorderLayout.EAST);
		btnNext.addActionListener(this);

		lblToday = new JLabel("Today");
		lblToday.setText(dateController.getTime(calendar));
		frmDiary.getContentPane().add(lblToday, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		frmDiary.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		mnSetting = new JMenuItem("Setting");
		mnMenu.add(mnSetting);
		mnSetting.addActionListener(this);

	}

	public void read(String date)
	{
		//读取日记
		String content = diaryController.getDiary(dir, date);
		// 读取时可以直接在每行后加上换行符 所以暂时不需要此方法
		// content = (new TextProcess()).readProcess(content);
		textPane.setText(content);
	}

	public void write(String date)
	{
		//写日记
		String inputStr = textPane.getText();
		inputStr = TextProcess.writeProcess(inputStr);
		(new DiaryController()).createDiary(inputStr, date);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource() == btnSave)
		{
			write(date);
			JOptionPane.showMessageDialog(null, "Saved successfully",
					"Success", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource() == btnLast)
		{
			calendar = dateController.getLastDate(calendar);
			date = dateController.getTime(calendar);
			lblToday.setText(dateController.getTime(calendar));
			read(date);
		}
		else if (e.getSource() == btnNext)
		{
			calendar = dateController.getNextDate(calendar);
			date = dateController.getTime(calendar);
			lblToday.setText(dateController.getTime(calendar));
			read(date);
		}
		else if (e.getSource() == mnSetting)
		{
			FrmSetting dialog = new FrmSetting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}

	}

}
