import org.libvirt.*;
import org.libvirt.jna.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;
import java.io.File;

public class lifecycle {
	
	private Connect conn;
	private String uri;

	public lifecycle()
	{
		conn = null;
	}

	public Domain create_hvm_domain(String uri,String xmlpath)
	{
		Domain d = null;
		try {
			conn = new Connect(uri,false);
			SAXReader reader = new SAXReader();
			Document docu = reader.read(new File(xmlpath));
			String xmlDesc = docu.asXML();
			d = conn.domainCreateXML(xmlDesc,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return d;	
	}

	public boolean start_hvm_domain(Domain domain)
	{
		try {
			domain.resume();
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}
		return true;
	}
	public boolean suspend_hvm_domain(Domain domain)
	{
		try {
			domain.suspend();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	public boolean close_hvm_domain(Domain domain)
	{
		try {
			domain.shutdown();
		}catch(Exception ee){
			ee.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		lifecycle test = new lifecycle();
		Domain d = test.create_hvm_domain("xen+ssh://root@172.17.1.19/","/home/guest_os.xml");
		test.start_hvm_domain(d);
		//suspend_hvm_domain(d);
		//close_hvm_domain(d);	
	}
}
