package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import control.NetworkCommand;
import exception.BusinessException;
import exception.RemoteException;
import model.TbMessage;

//import listener.ClientListener;

/**
 * 
 * 客户端主窗口
 */

public class ChatView extends JFrame implements ActionListener
{

	//	ClientListener clientListener;

	public JLabel receiver;//选择发送消息的接受者
	public static JTextArea messageShow;//客户端的信息显示
	JScrollPane messageScrollPane;//信息显示的滚动条

	JLabel express, sendToLabel, messageLabel;

	public JTextField sendMessage;//客户端消息的发送
	public JButton sendMessageButton;//发送消息
	public JTextField showStatus;//显示用户连接状态

	//建立菜单栏
	JMenuBar jMenuBar = new JMenuBar();
	//建立菜单组
	JMenu operateMenu = new JMenu("操作");

	JMenu conMenu = new JMenu("设置");

	JMenu helpMenu = new JMenu("帮助");
	public JMenuItem helpItem = new JMenuItem("帮助");

	//框架的大小
	Dimension faceSize = new Dimension(400, 600);

	JPanel downPanel;
	GridBagLayout girdBag;
	GridBagConstraints girdBagCon;

	UserNode userNode;

	public ChatView()
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
		//		this.setTitle(clientListener.userName); //设置标题
	}

	public ChatView(UserNode userNode)
	{
		this.userNode = userNode;

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
		//		this.setTitle(clientListener.userName); //设置标题
	}

	/**
	 * 程序初始化函数
	 */
	public void init()
	{
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//添加菜单栏
		jMenuBar.add(operateMenu);
		jMenuBar.add(conMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar(jMenuBar);

		receiver = new JLabel(userNode.getNickname());

		messageShow = new JTextArea();
		messageShow.setEditable(false);
		//添加滚动条
		messageScrollPane = new JScrollPane(messageShow,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400, 400));
		messageScrollPane.revalidate();

		sendMessage = new JTextField(23);
		//		clientMessage.setEnabled(false);
		sendMessageButton = new JButton();
		sendMessageButton.setText("发送");
		sendMessageButton.addActionListener(this);

		sendToLabel = new JLabel("发送至:");
		messageLabel = new JLabel("发送消息:");
		downPanel = new JPanel();
		girdBag = new GridBagLayout();
		downPanel.setLayout(girdBag);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 0;
		girdBagCon.gridwidth = 5;
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
		//girdBagCon.ipadx = 5;
		//girdBagCon.ipady = 5;
		girdBag.setConstraints(sendToLabel, girdBagCon);
		downPanel.add(sendToLabel);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
		girdBag.setConstraints(receiver, girdBagCon);
		downPanel.add(receiver);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 3;
		girdBagCon.gridy = 2;
		girdBagCon.anchor = GridBagConstraints.LINE_START;
		//girdBagCon.insets = new Insets(1,0,0,0);
		//girdBagCon.ipadx = 5;
		//girdBagCon.ipady = 5;

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 4;
		girdBagCon.gridy = 2;
		girdBagCon.insets = new Insets(1, 0, 0, 0);
		//girdBagCon.ipadx = 5;
		//girdBagCon.ipady = 5;

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(messageLabel, girdBagCon);
		downPanel.add(messageLabel);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 1;
		girdBagCon.gridy = 3;
		girdBagCon.gridwidth = 3;
		girdBagCon.gridheight = 1;
		girdBag.setConstraints(sendMessage, girdBagCon);
		downPanel.add(sendMessage);

		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 4;
		girdBagCon.gridy = 3;
		girdBag.setConstraints(sendMessageButton, girdBagCon);
		downPanel.add(sendMessageButton);

		showStatus = new JTextField(35);
		showStatus.setEditable(false);
		girdBagCon = new GridBagConstraints();
		girdBagCon.gridx = 0;
		girdBagCon.gridy = 5;
		girdBagCon.gridwidth = 5;
		girdBag.setConstraints(showStatus, girdBagCon);
		downPanel.add(showStatus);

		contentPane.add(messageScrollPane, BorderLayout.CENTER);
		contentPane.add(downPanel, BorderLayout.SOUTH);

		//		clientListener = new ClientListener(this);

		//关闭程序时的操作
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				//					if(clientListener.type == 1){
				//						clientListener.DisConnect();
				//					}
				System.exit(0);
			}
		});
	}

	public static void appendMessage(TbMessage message)
	{
		messageShow.append(message.getSendTime() + " "
				+ message.getFromUserId() + "\n");
		messageShow.append(message.getMessage() + "\n");
	}

	public int showConfirmDialog(String title, String msg)
	{
		int j = JOptionPane.showConfirmDialog(this, title, msg,
				JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (j == JOptionPane.YES_OPTION)
		{
			return 1;
		}
		return 0;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ChatView chatView = new ChatView();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource() == sendMessageButton)
		{
			TbMessage message = new TbMessage();
			message.setMessage(sendMessage.getText());
			message.setFromUserId(NetworkCommand.currentUser.getUserId());
			message.setSendTime(new Timestamp(new Date().getTime()));
			message.setToUserId(userNode.getId());
			//			message.setFromUserId(null);
			//			message.setSendTime(null);
			//			message.setToUserId(null);
			messageShow.append(message.getSendTime() + " "
					+ message.getFromUserId() + "\n");
			messageShow.append(message.getMessage() + "\n");
			sendMessage.setText("");
			System.out
					.println("from : " + message.getFromUserId() + " to : "
							+ message.getToUserId() + " message : "
							+ message.getMessage() + " time : "
							+ message.getSendTime());
			try
			{
				NetworkCommand.getServer().sendMessage(message);
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BusinessException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
