import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;

import server.RequestProto.Request;
import server.RequestProto.Response;
import server.RequestProto.UserCourse;
import org.libvirt.*;
import org.libvirt.jna.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;

public class VMList 
{
	private JFrame jframe;
	private JPanel jpanel;
	private JPanel original;
	private JProgressBar bar;
	private GridBagConstraints constraints;
	private GridBagLayout gridbag;
	private thread_ftp f;
	private Thread t;
	private Response res;	
	//public Domain domain;
	
	public VMList(JFrame in,JPanel org,Response response)
	{
		jframe = in;
		original = org;
		res = response;
	}
	private void init()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new NodeData(MYListType.ROOT,"My Virutal machines",null));
		int count = 1;
		for(UserCourse course: res.getUserCourseList())
		{
			DefaultMutableTreeNode coursenode = new DefaultMutableTreeNode(new NodeData(MYListType.COURSE,course.getCourseName(),null));
			DefaultMutableTreeNode vmnode = new DefaultMutableTreeNode(new NodeData(MYListType.VM,"vm"+count+"  "+course.getCourseName(),course.getImageAddress()));
			DefaultMutableTreeNode startnode = new DefaultMutableTreeNode(new NodeData(MYListType.START,"start",null));
			DefaultMutableTreeNode suspendnode = new DefaultMutableTreeNode(new NodeData(MYListType.SUSPEND,"suspend",null));
			DefaultMutableTreeNode closenode = new DefaultMutableTreeNode(new NodeData(MYListType.CLOSE,"close",null)); 
			root.add(coursenode);
			coursenode.add(vmnode);
			vmnode.add(startnode);
			vmnode.add(suspendnode);
			vmnode.add(closenode);
		}
	
		constraints = new GridBagConstraints();
		gridbag = new GridBagLayout();
		jpanel = new JPanel(){
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon("/root/student/LoginJFrameTest/src/img/method.png");
				img.paintIcon(this,g,0,0);
			}		
		};
		bar = new JProgressBar(); 
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setStringPainted(true);
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int width = screenSize.width; 
		int height = screenSize.height;
		try{
			try{
				JTree vmtree = new  JTree(root);
				vmtree.setFont(new Font("Calibri",Font.PLAIN,16));
				JScrollPane pane1 = new JScrollPane(vmtree);
				jpanel.setOpaque(true);
				jpanel.setLayout(gridbag);
				constraints.fill = GridBagConstraints.HORIZONTAL;
				constraints.ipadx = width/2;
				constraints.ipady = height/4;
				jpanel.add(pane1,constraints);
				
				vmtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				vmtree.addTreeSelectionListener(new TreeSelectionListener()
				{
					public void valueChanged(TreeSelectionEvent e)
					{
						if(e.getNewLeadSelectionPath().getLastPathComponent().toString().equals("start"))
						{
							try{
								constraints.gridx = 0;
								constraints.gridy = 1;
								constraints.weighty = 0.0;
								jpanel.add(bar,constraints);
								jframe.setContentPane(jpanel);
								f = new thread_ftp("Communication/hvm70.qcow","162.105.195.250","hongxx","pkueecshxx",bar);
								if(f!=null) {
									f.connectServer();
								}
								t = new Thread(f);
								t.start();
								//try {
								//	Connect conn;
								//	conn = new Connect("xen:///",false);
								//	SAXReader reader = new SAXReader();
								//	Document docu = reader.read(new File("/home/guest_os.xml"));
								//	String xmlDesc = docu.asXML();
								//	Domain domain = conn.domainCreateXML(xmlDesc,0);
								//	domain.resume();
								//}catch(Exception ee){
								//	ee.printStackTrace();
								//}
											
							}
							catch(Exception ee) { 
								ee.printStackTrace();
							}
						}
						/*else if(e.getNewLeadSelectionPath().getLastPathComponent().toString().equals("close"))
						{
							try {
								if(domain != null)
								{
									domain.shutdown();
								}							
							}catch(Exception ee){
								ee.printStackTrace();							
							}
						}
						*/
					}
				});
			}catch(Exception e){
				e.printStackTrace();
			}
			jframe.remove(original);
			jframe.setContentPane(jpanel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	interface MYListType 
	{
		int ROOT = 0;
		int COURSE = 1;
		int VM = 2;
		int START = 3;
		int SUSPEND = 4;
		int CLOSE = 5;
	}
	
	public void showMe()
		throws IOException
	{
		init();
		jframe.setVisible(true);
	}

	class NodeData 
	{
		public int nodeType;
		public String nodeData;
		public String path;

		public NodeData(int nodeType,String nodeData,String imagepath)
		{
			this.nodeType = nodeType;
			this.nodeData = nodeData;
			this.path = imagepath;
		}
		
		public String toString()
		{
			return nodeData;
		}
	}
	
	private static class MyRenderer extends DefaultTreeCellRenderer
	{
		ImageIcon rootIcon = new ImageIcon("icon/root.png");
		ImageIcon courseIcon = new ImageIcon("icon/course.png");
		ImageIcon vmIcon = new ImageIcon("icon/vm.png");
		ImageIcon startIcon = new ImageIcon("icon/start.png");
		ImageIcon suspendIcon = new ImageIcon("icon/suspend.png");
		ImageIcon closeIcon = new ImageIcon("icon/shutdown.png");
		
		public Component getTreeCellRendererComponent(JTree tree,Object value,
				boolean sel,boolean expanded, boolean leaf, int row, boolean hasFocus)
		{
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
			NodeData data = (NodeData)node.getUserObject();
			ImageIcon icon = null;
			switch(data.nodeType)
			{
			case MYListType.ROOT:
				icon = rootIcon;
				break;
			case MYListType.COURSE:
				icon = courseIcon;
				break;
			case MYListType.VM:
				icon = vmIcon;
				break;
			case MYListType.START:
				icon = startIcon;
				break;
			case MYListType.SUSPEND:
				icon = suspendIcon;
				break;
			case MYListType.CLOSE:
				icon = closeIcon;
				break;
			}
			this.setIcon(icon);
			return this;
		}
	}
}
