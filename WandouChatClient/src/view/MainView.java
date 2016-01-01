package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import model.TbUser;
import control.NetworkCommand;

/**
 * 主界面
 * */
public class MainView extends JFrame
{

	// 定义北部需要的组件
	private JLabel myIcon, myName, clientState;
	private JComboBox myState;
	private JTextField searchField;
	private ImageIcon icon;

	// 中部需要的组件
	private JTabbedPane tabWindow;
	private JPanel tabLinkman, jtp1_jpl2, jpt1_jpl3;
	private JTree jtree;
	//	private DefaultTreeModel tree1_treeModel, tree2_treeModel;
	private JScrollPane scrollPane;

	public MainView()
	{

		initNorth();
		//		initCenter();

		//设置运行时窗口的位置
		Dimension faceSize = new Dimension(260, 520);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.width - faceSize.getWidth()) / 2,
				(int) (screenSize.height - faceSize.getHeight()) / 2);

		this.setLayout(null);
		this.setSize(260, 520);
		this.setResizable(false);
		// 添加窗口的关闭事件
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent arg0)
			{
				System.exit(0);
			}
		});
		this.setIconImage((new ImageIcon("image/头像.gif").getImage()));
		this.setTitle("WandouChat");
		this.setVisible(true);
	}

	public MainView(List onlineUsers)
	{

		initNorth();
		initCenter(onlineUsers);

		//设置运行时窗口的位置
		Dimension faceSize = new Dimension(260, 520);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.width - faceSize.getWidth()) / 2,
				(int) (screenSize.height - faceSize.getHeight()) / 2);

		this.setLayout(null);
		this.setSize(260, 520);
		this.setResizable(false);
		// 添加窗口的关闭事件
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent arg0)
			{
				System.exit(0);
			}
		});
		this.setIconImage((new ImageIcon("image/头像.gif").getImage()));
		this.setTitle("WandouChat");
		this.setVisible(true);
	}

	// 初始化北部(头像显示、状态下拉框、昵称、搜索栏)
	public void initNorth()
	{
		// 头像显示
		Image image = getToolkit().getImage("image/qqImg1.jpg");
		image = image.getScaledInstance(100, 100, image.SCALE_DEFAULT);
		icon = new ImageIcon(image);
		myIcon = new JLabel();
		myIcon.setIcon(icon);
		myIcon.setBounds(10, 10, 100, 100);
		this.add(myIcon);

		// 下拉框显示
		String[] state =
		{ "隐身", "在线" };
		myState = new JComboBox(state);
		myState.setBounds(120, 10, 60, 20);
		this.add(myState);

		// 昵称显示
		myName = new JLabel();
		myName.setText("豌豆");
		myName.setBounds(120, 40, 130, 20);
		this.add(myName);

		//状态显示
		clientState = new JLabel();
		clientState.setText(NetworkCommand.getServer().showState());
		clientState.setBounds(80, 70, 150, 20);
		this.add(clientState);

		// 搜索栏显示
		searchField = new JTextField();
		searchField.setBounds(10, 120, 235, 30);
		this.add(searchField);
	}

	/*
		// 初始化中部(好友列表和群列表的显示)
		public void initCenter()
		{
			// 初始化好友列表
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(new UserNode(
					"0", "好友列表"));
			// 一级节点
			DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(new UserNode(
					"1", "我的好友"));
			DefaultMutableTreeNode node3 = new DefaultMutableTreeNode(new UserNode(
					"3", "黑名单"));

			// 子节点
			// 好友
			DefaultMutableTreeNode node1_1 = new DefaultMutableTreeNode(
					new UserNode("100", "张三", "image/1-1.gif"));
			DefaultMutableTreeNode node1_2 = new DefaultMutableTreeNode(
					new UserNode("400", "李四", "image/2-1.gif"));
			DefaultMutableTreeNode node1_3 = new DefaultMutableTreeNode(
					new UserNode("200", "王五", "image/3-1.gif"));
			// 陌生人
			DefaultMutableTreeNode node2_1 = new DefaultMutableTreeNode(
					new UserNode("1300", "张三", "image/4-1.gif"));
			DefaultMutableTreeNode node2_2 = new DefaultMutableTreeNode(
					new UserNode("4050", "张三", "image/7-1.gif"));
			// 黑名单
			DefaultMutableTreeNode node3_1 = new DefaultMutableTreeNode(
					new UserNode("1010", "张三", "image/2-1.gif"));
			DefaultMutableTreeNode node3_2 = new DefaultMutableTreeNode(
					new UserNode("4400", "张三", "image/5-1.gif"));
			DefaultMutableTreeNode node3_3 = new DefaultMutableTreeNode(
					new UserNode("20440", "张三", "image/8-1.gif"));

			jtree = new JTree(root);
			jtree.setCellRenderer(new MyCellRenderer());
			//	jpt1_tree1.setRootVisible(false);
			scrollPane = new JScrollPane(jtree);
			// 设置单击展开树节点
			jtree.setToggleClickCount(1);

			//设置双击监听
			jtree.addMouseListener(new FilePopupListener());

			root.add(node1);
			root.add(node3);

			node1.add(node1_1);
			node1.add(node1_2);
			node1.add(node1_3);

			node3.add(node3_1);
			node3.add(node3_2);
			node3.add(node3_3);

			// 创建选项卡窗口
			tabWindow = new JTabbedPane(JTabbedPane.TOP);
			// 选项卡一:好友列表

			tabLinkman = new JPanel(new BorderLayout());
			tabLinkman.add(scrollPane, "Center");

			//		jtp1_jpl2 = new JPanel();
			//		jpt1_jpl3 = new JPanel();

			tabWindow.add(tabLinkman, "联系人");
			//		tavWindow.add(jtp1_jpl2, "群");
			//		tavWindow.add(jpt1_jpl3, "最近联系人");

			tabWindow.setBounds(10, 160, 235, 280);
			this.add(tabWindow);

		}
		*/

	// 初始化中部(好友列表和群列表的显示)
	public void initCenter(List onlineUsers)
	{
		// 初始化好友列表
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new UserNode(
				"0", "好友列表"));
		for (int i = 0; i < onlineUsers.size(); i++)
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(
					new UserNode((TbUser) onlineUsers.get(i)));
			root.add(node);
		}

		jtree = new JTree(root);
		jtree.setCellRenderer(new MyCellRenderer());
		//	jpt1_tree1.setRootVisible(false);
		scrollPane = new JScrollPane(jtree);
		// 设置单击展开树节点
		jtree.setToggleClickCount(1);

		//设置双击监听
		jtree.addMouseListener(new FilePopupListener());

		// 创建选项卡窗口
		tabWindow = new JTabbedPane(JTabbedPane.TOP);
		// 选项卡一:好友列表

		tabLinkman = new JPanel(new BorderLayout());
		tabLinkman.add(scrollPane, "Center");

		tabWindow.add(tabLinkman, "联系人");
		//		tavWindow.add(jtp1_jpl2, "群");
		//		tavWindow.add(jpt1_jpl3, "最近联系人");

		tabWindow.setBounds(10, 160, 235, 280);
		this.add(tabWindow);

	}

	class MyCellRenderer extends DefaultTreeCellRenderer
	{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus)
		{

			super.getTreeCellRendererComponent(tree, value, selected, expanded,
					leaf, row, hasFocus);

			// 获得节点对象
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			UserNode userNode = (UserNode) node.getUserObject();
			String id = userNode.getId();
			String nickname = userNode.getNickname();
			String image = userNode.getImage();

			// 设置节点显示内容
			this.setText(nickname);

			// 设置叶节点的图标
			if (leaf && image != null)
			{
				this.setIcon(new ImageIcon(image));
			}
			//	// 设置展开的节点的图标
			//	if(expanded) {
			//	setOpenIcon(new ImageIcon("image/openFloder.jpg"));
			//	}
			//	else {
			//	setClosedIcon(new ImageIcon("image/closeFloder.jpg"));
			//	}
			return this;
		}
	}

	class FilePopupListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent me)
		{
			int n = jtree.getRowForLocation(me.getX(), me.getY());
			if (n < 0)
				return;
			TreePath selTree = jtree.getPathForRow(n);
			DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) selTree
					.getLastPathComponent();
			if (selNode.isLeaf())
			{
				if (SwingUtilities.isLeftMouseButton(me))
					if (me.getClickCount() == 2) // 表示鼠标双击
					{
						UserNode userNode = (UserNode) selNode.getUserObject();
						//						System.out.println(userNode.getId());
						new ChatView((JPanel) getContentPane(), userNode);
					}
				//                 leaf.show(jtree,me.getX(),me.getY());
			}
			/*			else
						{
							if (SwingUtilities.isRightMouseButton(me))
								//                 parent.show(jtree,me.getX(),me.getY());
								System.out.println("test2");
						}*/
		}
	}

	public static void main(String[] args)
	{
		new MainView();
	}

}