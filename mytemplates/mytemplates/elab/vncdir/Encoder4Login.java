import java.io.*;

public class Encoder4Login
{
	private String str;
	public Encoder4Login()
	{
		str = new String("");
	}
	public Encoder4Login(String a)
	{
		str = a;
	}
	public void setStr(String a)
	{
		str = a;
	}
	
	public boolean is_legal()
	{
		char temp;
		if((str.length() > 20) || (str.length() < 1))
		{
			return false;
		}
		else
		{
			for(int i=0;str.charAt(i)!='\0';i++)
			{
				temp = str.charAt(i);
				if((temp>='a' && temp<='z') ||
					(temp>='A' && temp<='Z') ||
					(temp>='0' && temp<='9')
				){
					return true;
				}
				else
					return false;
			}
		}
		return true;
	}

	public String getEncodedStr()
	{
		String result = new String("");
		int len = str.length();
		for(int i=0;i<20-len;i++)
		{
			result+='#';
		}
		result+=str;
		return result;
	}
}
