package ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import control.DateController;
import control.DiaryController;
import control.TextProcess;

public class Didary
{
	/*要用到的各个组件*/
	private static JTextPane textPane = new JTextPane();
	private static JButton btnSave = new JButton("保存");
	private static JButton btnLast = new JButton("前一天");
	private static JButton btnNext = new JButton("后一天");
	private static JLabel lblToday = new JLabel("今天");
	private static JLabel lblSetting = new JLabel("路径 ：");
	private static JTextField edtSetting = new JTextField(40);
	private static JButton btnOk = new JButton("确定");
	private static JButton btnCancel = new JButton("取消");
	private static JMenu mnMenu = new JMenu("菜单");
	private static JMenuItem mnSetting = new JMenuItem("设置");
	private static Calendar calendar = (new DateController())
			.initializeCalendar();
	private static DiaryController diaryController = new DiaryController();
	private static DateController dateController = new DateController();
	private static String date = dateController.getTime(calendar);
	//判断退出时是否已经保存
	private static boolean haveSaved = true;
	// 日记存放路径
	public static String dir = "H:\\MyDiary";
	//撤销管理类
	private static UndoManager um = new UndoManager();

	public static class myDiary extends JFrame implements ActionListener
	{
		/*用来切换的各个面板*/
		private JPanel mainPanel = new JPanel();
		private JPanel settingPanel = new JPanel();

		public myDiary()
		{
			//初始化frame窗体
			setTitle("Diary");
			setBounds(100, 100, 450, 300);
			setLocationRelativeTo(null);// 使窗体居中显示

			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnMenu = new JMenu("菜单");
			menuBar.add(mnMenu);

			mnSetting = new JMenuItem("设置");
			mnMenu.add(mnSetting);
			mnSetting.addActionListener(this);

			//设置点击右上角时不作任何操作
			//		frmDiary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			//初始化各版面
			initMainPanel();
			add(mainPanel);
			mainPanel.setVisible(true);
			initSettingPanel();
			add(settingPanel);
			settingPanel.setVisible(false);

			// 键盘全局监听
			Toolkit.getDefaultToolkit().addAWTEventListener(
					new AWTEventListener()
					{
						public void eventDispatched(AWTEvent event)
						{
							if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED)
							{
								// 放入自己的键盘监听事件
								// System.out.println(((KeyEvent) event).getKeyChar());
//								 System.out.println(((KeyEvent) event).getKeyCode());
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
								else if (((KeyEvent) event).getModifiers() == KeyEvent.CTRL_MASK
										&& ((KeyEvent) event).getKeyCode() == 90)
								{
									//ctrl+z实现撤销功能
									um.undo();
								}

							}
						}
					}, AWTEvent.KEY_EVENT_MASK);

			//窗口关闭监听
			addWindowListener(new WindowAdapter()
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

		/*初始化主面板*/
		public void initMainPanel()
		{
			mainPanel.setBounds(0, 0, 435, 235);
			mainPanel.setLayout(new BorderLayout(0, 0));

			//设置字体
			textPane.setFont(new Font("方正喵呜体", Font.PLAIN, 16));
			textPane.getDocument().addDocumentListener(
					new Swing_OnValueChanged());
			read(date);
			mainPanel.add(textPane, BorderLayout.CENTER);

			btnSave.setBorder(BorderFactory.createEtchedBorder());// 蚀刻样式
			mainPanel.add(btnSave, BorderLayout.SOUTH);
			btnSave.addActionListener(this);

			btnLast.setBorder(BorderFactory.createEtchedBorder());
			mainPanel.add(btnLast, BorderLayout.WEST);
			btnLast.addActionListener(this);

			btnNext.setBorder(BorderFactory.createEtchedBorder());
			mainPanel.add(btnNext, BorderLayout.EAST);
			btnNext.addActionListener(this);

			lblToday.setText(dateController.getTime(calendar));
			mainPanel.add(lblToday, BorderLayout.NORTH);

			textPane.getDocument().addUndoableEditListener(
					new UndoableEditListener()
					{//注册撤销可编辑监听器
						public void undoableEditHappened(UndoableEditEvent e)
						{
							um.addEdit(e.getEdit());
						}

					});//编辑撤销的监听

		}

		/*初始化设置面板*/
		public void initSettingPanel()
		{
			settingPanel.setBounds(0, 0, 435, 235);
			settingPanel.setLayout(null);

			lblSetting.setBounds(100, 80, 50, 20);
			settingPanel.add(lblSetting);

			dir = dir.replace("\\", "\\\\");
			edtSetting.setText(dir);
			edtSetting.setBounds(160, 80, 150, 20);
			settingPanel.add(edtSetting);

			btnOk.setBounds(240, 180, 70, 30);
			settingPanel.add(btnOk);
			btnOk.addActionListener(this);

			btnCancel.setBounds(330, 180, 70, 30);
			settingPanel.add(btnCancel);
			btnCancel.addActionListener(this);
		}

		/*读取日记操作*/
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

		/*写日记操作*/
		public void write(String date)
		{
			// 写日记
			String inputStr = textPane.getText();
			inputStr = TextProcess.writeProcess(inputStr);
			(new DiaryController()).createDiary(inputStr, dir, date);
		}

		/*按键监听*/
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
				mainPanel.setVisible(false);
				settingPanel.setVisible(true);
			}
			else if (e.getSource() == btnOk)
			{
				dir = edtSetting.getText();
				JOptionPane.showMessageDialog(null, "保存成功", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				mainPanel.setVisible(true);
				settingPanel.setVisible(false);
			}
			else if (e.getSource() == btnCancel)
			{
				mainPanel.setVisible(true);
				settingPanel.setVisible(false);
			}
		}

		//根据接口DocumentListener定义新类Swing_OnValueChanged 
		class Swing_OnValueChanged implements DocumentListener
		{ //输出变化及结果 
			public void changedUpdate(DocumentEvent e)
			{
				//				System.out.println("Attribute Changed");
			}

			public void insertUpdate(DocumentEvent e)
			{ //输出变化及结果 
			//				System.out.println("Text Inserted");
				//每次写日记的时候 还原是否保存的标志位
				haveSaved = false;
			}

			public void removeUpdate(DocumentEvent e)
			{ //输出变化及结果 
			//				System.out.println("Text Removed");
			}
		}
	}

	public static void main(String[] args)
	{
		//test
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					myDiary user = new myDiary();
					user.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
