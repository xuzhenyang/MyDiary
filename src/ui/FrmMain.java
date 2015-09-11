package ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Toolkit;

public class FrmMain implements ActionListener
{

	private JFrame frmDiary;
	private JButton btnSave;
	private JTextPane textPane = new JTextPane();
	private JButton btnLast;
	private JButton btnNext;
	private JLabel lblToday;
	private JMenuItem mnSetting;
	private static Calendar calendar = (new DateController())
			.initializeCalendar();
	private DiaryController diaryController = new DiaryController();
	private DateController dateController = new DateController();
	private String date = dateController.getTime(calendar);
	//判断退出时是否已经保存
	private boolean haveSaved = true;
	// 日记存放路径
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
		// 键盘全局监听
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
		{
			public void eventDispatched(AWTEvent event)
			{
				if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED)
				{
					// 放入自己的键盘监听事件
					// System.out.println(((KeyEvent) event).getKeyChar());
					// System.out.println(((KeyEvent) event).getKeyCode());
					// ((KeyEvent) event).getKeyCode();// 获取按键的code
					// ((KeyEvent) event).getKeyChar();// 获取按键的字符

					// save键的快捷键实现
					if (((KeyEvent) event).getModifiers() == KeyEvent.CTRL_MASK
							&& ((KeyEvent) event).getKeyCode() == 83)
					{
						btnSave.doClick();
					}
					else if (((KeyEvent) event).getModifiers() == KeyEvent.CTRL_MASK
							&& ((KeyEvent) event).getKeyCode() == 37)
					{
						btnLast.doClick();
					}
					else if (((KeyEvent) event).getModifiers() == KeyEvent.CTRL_MASK
							&& ((KeyEvent) event).getKeyCode() == 39)
					{
						btnNext.doClick();
					}

				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
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

		//设置点击右上角时不作任何操作
		//		frmDiary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDiary.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//设置字体
		textPane.setFont(new Font("方正喵呜体", Font.PLAIN, 16));
		textPane.getDocument().addDocumentListener(new Swing_OnValueChanged());
		read(date);
		frmDiary.getContentPane().add(textPane, BorderLayout.CENTER);

		btnSave = new JButton("保存");
		btnSave.setBorder(BorderFactory.createEtchedBorder());// 蚀刻样式
		frmDiary.getContentPane().add(btnSave, BorderLayout.SOUTH);
		btnSave.addActionListener(this);

		btnLast = new JButton("前一天");
		btnLast.setBorder(BorderFactory.createEtchedBorder());
		frmDiary.getContentPane().add(btnLast, BorderLayout.WEST);
		btnLast.addActionListener(this);

		btnNext = new JButton("后一天");
		btnNext.setBorder(BorderFactory.createEtchedBorder());
		frmDiary.getContentPane().add(btnNext, BorderLayout.EAST);
		btnNext.addActionListener(this);

		lblToday = new JLabel("今天");
		lblToday.setText(dateController.getTime(calendar));
		frmDiary.getContentPane().add(lblToday, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		frmDiary.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("菜单");
		menuBar.add(mnMenu);

		mnSetting = new JMenuItem("设置");
		mnMenu.add(mnSetting);
		mnSetting.addActionListener(this);

		//窗口关闭监听
		frmDiary.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				if (haveSaved == false)
				{
					JOptionPane.showMessageDialog(null, "未保存", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					//					return;
				}
				else
				{
					System.exit(0);
				}
			}
		});

	}

	public void read(String date)
	{
		// 读取日记
		String content = diaryController.getDiary(dir, date);
		// 读取时可以直接在每行后加上换行符 所以暂时不需要此方法
		// content = (new TextProcess()).readProcess(content);
		textPane.setText(content);
		//每次读取日记的时候 先将标志位设置为已保存
		haveSaved = true;
	}

	public void write(String date)
	{
		// 写日记
		String inputStr = textPane.getText();
		inputStr = TextProcess.writeProcess(inputStr);
		(new DiaryController()).createDiary(inputStr, dir, date);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource() == btnSave)
		{
			write(date);
			JOptionPane.showMessageDialog(null, "保存成功", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			//保存后 修改标志位
			haveSaved = true;
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

	//根据接口DocumentListener定义新类Swing_OnValueChanged 
	class Swing_OnValueChanged implements DocumentListener
	{ //输出变化及结果 
		public void changedUpdate(DocumentEvent e)
		{
			System.out.println("Attribute Changed");
		}

		public void insertUpdate(DocumentEvent e)
		{ //输出变化及结果 
			System.out.println("Text Inserted");
			//每次写日记的时候 还原是否保存的标志位
			haveSaved = false;
		}

		public void removeUpdate(DocumentEvent e)
		{ //输出变化及结果 
			System.out.println("Text Removed");
		}
	}

}
