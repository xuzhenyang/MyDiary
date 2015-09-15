package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FrmSetting extends JDialog implements ActionListener
{

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			FrmSetting dialog = new FrmSetting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FrmSetting()
	{
		setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);// 使窗体居中显示
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JLabel labelHint = new JLabel(
					"\u65E5\u8BB0\u4FDD\u5B58\u8DEF\u5F84\uFF1A");
			contentPanel.add(labelHint);
		}
		{
			textField = new JTextField();
			//始终显示dir
			textField.setText(FrmMain.dir);
			contentPanel.add(textField);
			textField.setColumns(30);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnMenu = new JMenu("Menu");
				menuBar.add(mnMenu);
				{
					JMenuItem menuSetting = new JMenuItem("Setting");
					mnMenu.add(menuSetting);
				}
			}
		}
	}
	
	public String getText()
	{
		return textField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getSource() == okButton)
		{
			FrmMain.dir = this.getText();
			this.setVisible(false);
		}
		else if(e.getSource() == cancelButton)
		{
			this.setVisible(false);
		}
		
	}


}
