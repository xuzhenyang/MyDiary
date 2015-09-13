package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

			//���õ�����Ͻ�ʱ�����κβ���
			//		frmDiary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			//��ʼ��������
			initMainPanel();
			add(mainPanel);
			
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
			mainPanel.setLayout(new BorderLayout(0, 0));

			//��������
			textPane.setFont(new Font("����������", Font.PLAIN, 16));
			textPane.getDocument().addDocumentListener(new Swing_OnValueChanged());
			read(date);
			mainPanel.add(textPane, BorderLayout.CENTER);

			btnSave = new JButton("����");
			btnSave.setBorder(BorderFactory.createEtchedBorder());// ʴ����ʽ
			mainPanel.add(btnSave, BorderLayout.SOUTH);
			btnSave.addActionListener(this);

			btnLast = new JButton("ǰһ��");
			btnLast.setBorder(BorderFactory.createEtchedBorder());
			mainPanel.add(btnLast, BorderLayout.WEST);
			btnLast.addActionListener(this);

			btnNext = new JButton("��һ��");
			btnNext.setBorder(BorderFactory.createEtchedBorder());
			mainPanel.add(btnNext, BorderLayout.EAST);
			btnNext.addActionListener(this);

			lblToday = new JLabel("����");
			lblToday.setText(dateController.getTime(calendar));
			mainPanel.add(lblToday, BorderLayout.NORTH);

			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnMenu = new JMenu("�˵�");
			menuBar.add(mnMenu);

			mnSetting = new JMenuItem("����");
			mnMenu.add(mnSetting);
			mnSetting.addActionListener(this);
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
//				FrmSetting dialog = new FrmSetting();
//				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				dialog.setVisible(true);
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
