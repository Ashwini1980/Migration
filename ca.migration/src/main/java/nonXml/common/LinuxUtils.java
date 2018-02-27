package nonXml.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class LinuxUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LinuxUtils.class); 
	
    static Properties prop = null;    
    static JSch jsch = null;
    static Session session = null;
    static Channel channel = null;
	
	@Test (description = "Connecting to linux server")	
	public static void connectToLinux (String uName, String pwd, String host) {
		
	    prop = new Properties();
	    prop.put("StrictHostKeyChecking", "no");
	    jsch = new JSch();

	    try {
	    	session = jsch.getSession(uName, host, 22);	    	
		    session.setPassword(pwd);
		    session.setConfig(prop);
		    session.connect();		    	    

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    LOGGER.info("Connected to the linux server successfully...");
			
	}
	
	
/*	@Test (dependsOnMethods="connectToLinux")
	
	public static ArrayList<String> setCommand (String command) {
		
		ArrayList <String> al = new ArrayList<String>();
		
		try {
			
			channel=session.openChannel("exec");
		    ((ChannelExec)channel).setCommand(command);
			
		    channel.setInputStream(null);
		    ((ChannelExec)channel).setErrStream(System.err);
		    
		    InputStream in=channel.getInputStream();
		    channel.connect();
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String result;                          
		    while ((result = br.readLine()) != null)
		   {
		    	al.add(result);                    
		   }		    
		    
		    
		    
		
		    br.close();
		    byte[] tmp=new byte[1024];
		    while(true){
		      while(in.available()>0){
		        int i=in.read(tmp, 0, 1024);
		        if(i<0)break;
		        System.out.print(new String(tmp, 0, i));
                
		      }
		      if(channel.isClosed()){
		       // System.out.println("exit-status: "+channel.getExitStatus());
		        break;
		      }
		      try{
		    	  Thread.sleep(1000);
		    	  }catch(Exception ee){}
		   }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    //channel.disconnect();
		    //session.disconnect();
			
		}
		System.out.println("InputStream is: "+al);
		return al;
	}*/
	
	//Store the results after running the commands
	@Test (dependsOnMethods="connectToLinux")
	
	public static ArrayList<String> getResult (String command) {
		
		ArrayList <String> al = new ArrayList<String>();
		
		try {
			
			channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			
		    channel.setInputStream(null);
		    ((ChannelExec)channel).setErrStream(System.err);
		    
		    InputStream in=channel.getInputStream();
		    channel.connect();
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String result;                          
		    while ((result = br.readLine()) != null)
		   {
		    	al.add(result);                    
		   }		
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 		
		
		return al; 
	
	}
	
	@Test (dependsOnMethods="connectToLinux")
	
	public static void closeConnection () {
		
		try {
			
			channel=session.openChannel("exec");
			
		    channel.setInputStream(null);
		    ((ChannelExec)channel).setErrStream(System.err);
		    channel.connect();
		    
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			 channel.disconnect();
		     session.disconnect();
		}
		
	}
	
	
}
