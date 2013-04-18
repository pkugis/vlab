import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import server.RequestProto.Request;
import server.RequestProto.Response;
import server.RequestProto.UserCourse;

public class Login
	implements ActionListener 
{
	private JFrame jframe;
	private JLabel jlabel,jlabel1;
	private GridBagLayout gridbag;
	private GridBagConstraints constraints;
	private JTextField jtfield1;
	private JPasswordField jpfield1;
	private JButton jbutton1,jbutton2;
	private JPanel jpanel,jpanel_added;
	private boolean SocketConnected;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private Encoder4Login loginencoder1;
	private Encoder4Login loginencoder2;
	
	public Login()
	{
		jframe = new JFrame("");
		jlabel = new JLabel();
		jlabel1 = new JLabel();
		jtfield1 = new JTextField();
		jpfield1 = new JPasswordField();
		gridbag = new GridBagLayout();
		jbutton1 = new JButton();
		jbutton2 = new JButton();
		SocketConnected = false;
		loginencoder1 = new Encoder4Login();
		loginencoder2 = new Encoder4Login();
		init();
	}

	private void init()
	{
		jframe.setTitle("EECS Virtual Lab");
		jpanel = new JPanel(){
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon("/root/student/LoginJFrameTest/src/img/method.png");
				img.paintIcon(this, g, 0, 0);
			}
		};
		jlabel.setText("UserName:");
		jlabel1.setText("Password:");
		jbutton1.setText("Log in");
		jbutton2.setText("Exit");
		jframe.setUndecorated(true);
		jframe.getGraphicsConfiguration().getDevice().setFullScreenWindow(jframe);
		jpanel.setOpaque(true);
		jpanel.setLayout(gridbag);
		 
	     	constraints = getGridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,
	     	GridBagConstraints.NONE,new Insets(10,0,10,0),0,0);
	     	gridbag.setConstraints(jlabel, constraints);
	     	jpanel.add(jlabel,constraints,0);
	     
	    	constraints = getGridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,
	     	GridBagConstraints.NONE,new Insets(10,0,10,0),100,0);
	     	gridbag.setConstraints(jtfield1, constraints);
	     	jpanel.add(jtfield1,constraints,1);

	     	constraints = getGridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,
	     	GridBagConstraints.NONE,new Insets(10,0,10,0),0,0);
	     	gridbag.setConstraints(jlabel1, constraints);
	     	jpanel.add(jlabel1,constraints,2);
	 
	     	constraints = getGridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,
	     	GridBagConstraints.NONE,new Insets(10,0,10,0),100,0);
	     	gridbag.setConstraints(jpfield1, constraints);
	     	jpanel.add(jpfield1,constraints,3);
	  
	     	constraints = getGridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,
	     	GridBagConstraints.NONE,new Insets(10,0,10,0),0,0);
	     	gridbag.setConstraints(jbutton1, constraints);
	     	jpanel.add(jbutton1,constraints,4);
	     	jbutton1.addActionListener(this);
	
		constraints = getGridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,
		GridBagConstraints.NONE,new Insets(10,0,10,0),0,0);
		gridbag.setConstraints(jbutton2, constraints);
		jpanel.add(jbutton2,constraints,5);
		jbutton2.addActionListener(this);
		jframe.add(jpanel);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getActionCommand().equals("Log in"))
		{
			try {
				socket = new Socket("localhost", 10000);
				System.out.println("connect success");
				in = socket.getInputStream();
				out = socket.getOutputStream();
				String a = jtfield1.getText();
				String b = String.valueOf(jpfield1.getPassword());
				loginencoder1.setStr(a);
				loginencoder2.setStr(b);
				if(loginencoder1.is_legal() && loginencoder2.is_legal())
				{
					a = loginencoder1.getEncodedStr();
					System.out.println(a);
					b = loginencoder2.getEncodedStr();
					System.out.println(b);
			
					Request request = Request.newBuilder()
						.setUserId(a)
						.setPassword(b)
						.build();
					Response response;
					System.out.println(request.toString());
					request.writeTo(out);
					System.out.println("request size : " + request.getSerializedSize());
					while (in.available() == 0) {}
					System.out.println("in avaliable : " + in.available());
					response = Response.parseFrom(in);
					System.out.println(response.getIsLegal());
					if(!response.getIsLegal())
					{
						System.out.println("authorization failed");
						socket.close();	
					}
					else{
						for(UserCourse course: response.getUserCourseList())
						{
							System.out.println(course.getCourseId());
							System.out.println(course.getCourseName());
							System.out.println(course.getImageAddress());
							System.out.println(course.getAuthority());
						}
						socket.close(); 
						System.out.println("close connect");		
						try {	
							VMList vl = new VMList(jframe,jpanel,response);
							vl.showMe();
						}catch(Exception e){
							System.out.println(e.getMessage());		
						}
					}
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else if(evt.getActionCommand().equals("Exit"))
		{
			System.exit(-1);
		}
		else
		{
			System.out.println("unknown evt!");
		}
	}
	 
	public static GridBagConstraints getGridBagConstraints(
		int gridx,int gridy,int gridwidth, int gridheight,
		double weightx,double weighty,int anchor,int fill,
		Insets insets,int ipadx,int ipady)
	{
		return new GridBagConstraints
		(	gridx, gridy, gridwidth, gridheight, weightx, 
			weighty, anchor, fill, insets, ipadx, ipady
		);
	}
	 
	public void showMe()
	{
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.setVisible(true);
	}
	 
	public static void main(String[] args) 
	{
	  	new Login().showMe();
	}
}
