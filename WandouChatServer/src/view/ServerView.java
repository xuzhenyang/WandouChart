package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import network.ServerListener;

public class ServerView extends JFrame
{

	ServerListener sendInfo;

	public JComboBox combobox;//选择发送消息的接受者,列表显示
	public JTextArea messageShow;//服务端的信息显示
	JScrollPane messageScrollPane;//信息显示的滚动条
	public JTextField showStatus;//显示用户连接状态，在线人数
	JLabel sendToLabel, messageLabel;
	public JTextField sysMessage;//服务端消息的发送，写信息
	public JButton sysMessageButton;//服务端消息的发送按钮

	//建立工具栏
	JToolBar toolBar = new JToolBar();

	//建立工具栏中的按钮组件
	public JButton portSet;//启动服务端侦听
	public JButton startServer;//启动服务端侦听
	public JButton stopServer;//关闭服务端侦听
	public JButton exitButton;//退出按钮

	//框架的大小
	Dimension faceSize = new Dimension(400, 600);

	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;

	/**
	 * 构造函数
	 */
	public ServerView()
	{
		init();//初始化程序

		//添加框架的关闭事件处理
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//设置框架的大小
		this.setSize(faceSize);
		this.setVisible(true);
		//设置运行时窗口的位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.width - faceSize.getWidth()) / 2,
				(int) (screenSize.height - faceSize.getHeight()) / 2);
		this.setResizable(false);

		this.setTitle("聊天室服务端"); //设置标题

	}

	/**
	 * 程序初始化函数
	 */
	public void init()
	{

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//初始化按钮
		portSet = new JButton("端口设置");
		startServer = new JButton("启动服务");
		stopServer = new JButton("停止服务");
		exitButton = new JButton("退出");
		//将按钮添加到工具栏
		toolBar.add(portSet);
		toolBar.addSeparator();//添加分隔栏
		toolBar.add(startServer);
		toolBar.add(stopServer);
		toolBar.addSeparator();//添加分隔栏
		toolBar.add(exitButton);
		contentPane.add(toolBar, BorderLayout.NORTH);

		//初始时，令停止服务按钮不可用
		stopServer.setEnabled(false);

		combobox = new JComboBox();
		combobox.insertItemAt("所有人", 0);

		messageShow = new JTextArea();
		messageShow.setEditable(false);
		//添加滚动条
		messageScrollPane = new JScrollPane(messageShow,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();

		showStatus = new JTextField(35);
		showStatus.setEditable(false);

		sysMessage = new JTextField(24);
		sysMessage.setEnabled(false);
		sysMessageButton = new JButton();
		sysMessageButton.setText("发送");

		sendToLabel = new JLabel("发送至:");
		messageLabel = new JLabel("发送消息:");
		downPanel = new JPanel();
		girdBag = new GridBagLayout();
		downPanel.setLayout(girdBag);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 0;
		girdBagCon.gridwidth = 3;
		girdBagCon.gridheight = 2;
		girdBagCon.ipadx = 5;
		girdBagCon.ipady = 5;
		JLabel none = new JLabel("    ");
		girdBag.setConstraints(none, girdBagCon);
		downPanel.add(none);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 2;
		girdBagCon.insets = new Insets(1, 0, 0, 0);
		girdBagCon.ipadx = 5;
		girdBagCon.ipady = 5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
		girdBag.setConstraints(combobox, girdBagCon);
		downPanel.add(combobox);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(sysMessage, girdBagCon);
		downPanel.add(sysMessage);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 2;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(sysMessageButton, girdBagCon);
		downPanel.add(sysMessageButton);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 4;
		girdBagCon.gridwidth = 3;
		girdBag.setConstraints(showStatus, girdBagCon);
		downPanel.add(showStatus);

		contentPane.add(messageScrollPane, BorderLayout.CENTER);
		contentPane.add(downPanel, BorderLayout.SOUTH);

		sendInfo = new ServerListener(this);

		//关闭程序时的操作
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				//					sendInfo.stopService();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ServerView serverView = new ServerView();
	}

}
