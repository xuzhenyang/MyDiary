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
	/*Ҫ�õ��ĸ������*/
	private static JTextPane textPane = new JTextPane();
	private static JButton btnSave = new JButton("����");
	private static JButton btnLast = new JButton("ǰһ��");
	private static JButton btnNext = new JButton("��һ��");
	private static JLabel lblToday = new JLabel("����");
	private static JLabel lblSetting = new JLabel("·�� ��");
	private static JTextField edtSetting = new JTextField(40);
	private static JButton btnOk = new JButton("ȷ��");
	private static JButton btnCancel = new JButton("ȡ��");
	private static JMenu mnMenu = new JMenu("�˵�");
	private static JMenuItem mnSetting = new JMenuItem("����");
	private static Calendar calendar = (new DateController())
			.initializeCalendar();
	private static DiaryController diaryController = new DiaryController();
	private static DateController dateController = new DateController();
	private static String date = dateController.getTime(calendar);
	//�ж��˳�ʱ�Ƿ��Ѿ�����
	private static boolean haveSaved = true;
	// �ռǴ��·��
	public static String dir = "H:\\MyDiary";
	//����������
	private static UndoManager um = new UndoManager();

	public static class myDiary extends JFrame implements ActionListener
	{
		/*�����л��ĸ������*/
		private JPanel mainPanel = new JPanel();
		private JPanel settingPanel = new JPanel();

		public myDiary()
		{
			//��ʼ��frame����
			setTitle("Diary");
			setBounds(100, 100, 450, 300);
			setLocationRelativeTo(null);// ʹ���������ʾ

			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnMenu = new JMenu("�˵�");
			menuBar.add(mnMenu);

			mnSetting = new JMenuItem("����");
			mnMenu.add(mnSetting);
			mnSetting.addActionListener(this);

			//���õ�����Ͻ�ʱ�����κβ���
			//		frmDiary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			//��ʼ��������
			initMainPanel();
			add(mainPanel);
			mainPanel.setVisible(true);
			initSettingPanel();
			add(settingPanel);
			settingPanel.setVisible(false);

			// ����ȫ�ּ���
			Toolkit.getDefaultToolkit().addAWTEventListener(
					new AWTEventListener()
					{
						public void eventDispatched(AWTEvent event)
						{
							if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED)
							{
								// �����Լ��ļ��̼����¼�
								// System.out.println(((KeyEvent) event).getKeyChar());
//								 System.out.println(((KeyEvent) event).getKeyCode());
								// ((KeyEvent) event).getKeyCode();// ��ȡ������code
								// ((KeyEvent) event).getKeyChar();// ��ȡ�������ַ�

								// save���Ŀ�ݼ�ʵ��
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
									//ctrl+zʵ�ֳ�������
									um.undo();
								}

							}
						}
					}, AWTEvent.KEY_EVENT_MASK);

			//���ڹرռ���
			addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					if (haveSaved == false)
					{
						JOptionPane.showMessageDialog(null, "δ����", "��ʾ",
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

		/*��ʼ�������*/
		public void initMainPanel()
		{
			mainPanel.setBounds(0, 0, 435, 235);
			mainPanel.setLayout(new BorderLayout(0, 0));

			//��������
			textPane.setFont(new Font("����������", Font.PLAIN, 16));
			textPane.getDocument().addDocumentListener(
					new Swing_OnValueChanged());
			read(date);
			mainPanel.add(textPane, BorderLayout.CENTER);

			btnSave.setBorder(BorderFactory.createEtchedBorder());// ʴ����ʽ
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
					{//ע�᳷���ɱ༭������
						public void undoableEditHappened(UndoableEditEvent e)
						{
							um.addEdit(e.getEdit());
						}

					});//�༭�����ļ���

		}

		/*��ʼ���������*/
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

		/*��ȡ�ռǲ���*/
		public void read(String date)
		{
			// ��ȡ�ռ�
			String content = diaryController.getDiary(dir, date);
			// ��ȡʱ����ֱ����ÿ�к���ϻ��з� ������ʱ����Ҫ�˷���
			// content = (new TextProcess()).readProcess(content);
			textPane.setText(content);
			//ÿ�ζ�ȡ�ռǵ�ʱ�� �Ƚ���־λ����Ϊ�ѱ���
			haveSaved = true;
		}

		/*д�ռǲ���*/
		public void write(String date)
		{
			// д�ռ�
			String inputStr = textPane.getText();
			inputStr = TextProcess.writeProcess(inputStr);
			(new DiaryController()).createDiary(inputStr, dir, date);
		}

		/*��������*/
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			if (e.getSource() == btnSave)
			{
				write(date);
				JOptionPane.showMessageDialog(null, "����ɹ�", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				//����� �޸ı�־λ
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
				JOptionPane.showMessageDialog(null, "����ɹ�", "��ʾ",
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

		//���ݽӿ�DocumentListener��������Swing_OnValueChanged 
		class Swing_OnValueChanged implements DocumentListener
		{ //����仯����� 
			public void changedUpdate(DocumentEvent e)
			{
				//				System.out.println("Attribute Changed");
			}

			public void insertUpdate(DocumentEvent e)
			{ //����仯����� 
			//				System.out.println("Text Inserted");
				//ÿ��д�ռǵ�ʱ�� ��ԭ�Ƿ񱣴�ı�־λ
				haveSaved = false;
			}

			public void removeUpdate(DocumentEvent e)
			{ //����仯����� 
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
