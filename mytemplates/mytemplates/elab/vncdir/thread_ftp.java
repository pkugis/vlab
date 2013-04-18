import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import sun.net.*;
import sun.net.ftp.FtpClient;
import javax.swing.JProgressBar;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;
import org.libvirt.*;
import org.libvirt.jna.*;

public class thread_ftp implements Runnable
{
	private FtpClient ftpclient;
	private String filepath;
	private String serverip;
	private String username;
	private String passwd;
	private long currentsize;
	private long transsize;
	private JProgressBar bar;	
	//private Domain domain;	
	
	public thread_ftp(String path,String server,String u,String p,JProgressBar inbar)
	{
		filepath = path;
		serverip = server;
		username = u;
		passwd = p;
		currentsize = 0;
		ftpclient = new FtpClient();
		bar = inbar;
		//indomain = domain;
	}
	
	public void connectServer() 
		throws IOException
	{
		try{
			ftpclient.openServer(serverip);
			ftpclient.login(username,passwd);
			System.out.println("connect to ftp server successfully");
			ftpclient.binary();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeServer()
		throws IOException
	{
		try {
			if(ftpclient != null)
				ftpclient.closeServer();	
		}catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
		
	public void run()
	{
		System.out.println("A new thread start for file downloading");
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			is = ftpclient.get(filepath);
			File outfile = new File("/root/hvm70.qcow");
			currentsize = outfile.length();
			os = new FileOutputStream(outfile);
			byte[] bytes = new byte[4096];
			int c;
			while((c = is.read(bytes))!=-1)
			{
				os.write(bytes,0,c);
				transsize = transsize+c;
				currentsize = outfile.length();
				bar.setString(currentsize/1024+"KB");
			}
			try {
				Thread.sleep(5000);
				Connect conn;
				conn = new Connect("xen:///",false);
				SAXReader reader = new SAXReader();
				Document docu = reader.read(new File("/root/guest_os.xml"));
				String xmlDesc = docu.asXML();
			    Domain domain = conn.domainCreateXML(xmlDesc,0);
				domain.resume();
				Thread.sleep(60000);
			    VncViewer v = new VncViewer();
			    String argv[] = new String[4];
			    argv[0] = "HOST";
			    argv[1] = "162.105.30.52";
			    argv[2] = "PORT";
			    argv[3] = "5900";
			    v.mainArgs = argv;
			    v.inAnApplet = false;
			    v.inSeparateFrame = true;

			    v.init();
			    v.start();
				// return domain;
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(is!=null){
					is.close();
				}
				if(os!=null){
					os.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		System.out.println("the thread stopped");
		try{
			if(ftpclient != null)
				ftpclient.closeServer();
		}catch(IOException e)
		{
			e.printStackTrace();
		}				
	}
	
	public long getCurrentValue()
	{
		return currentsize;
	}
	/*
	public static void main(String[] args)
		throws IOException
	{
		thread_ftp f = new thread_ftp("/lvvms/images/hvmxx/hvm70.qcow","172.17.1.9","hongxx","hong1991");
		f.connectServer();
		new Thread(f).start();
		f.closeServer();
	}
	*/
				
}
