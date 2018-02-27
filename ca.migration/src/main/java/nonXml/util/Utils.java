package nonXml.util;

import java.io.File;
import java.util.ArrayList;
import nonXml.common.LinuxUtils;

public class Utils {
	
	private static String base_em_home;
	private static String fresh_em_home;
	private static String upgrade_em_home;
	private static String custom_conf_location;
	
	public static String getBase_em_home() {
		return base_em_home;
	}
	public static void setBase_em_home(String base_em_home) {
		Utils.base_em_home = base_em_home;
	}
	public static String getFresh_em_home() {
		return fresh_em_home;
	}
	public static void setFresh_em_home(String fresh_em_home) {
		Utils.fresh_em_home = fresh_em_home;
	}
	public static String getUpgrade_em_home() {
		return upgrade_em_home;
	}
	public static void setUpgrade_em_home(String upgrade_em_home) {
		Utils.upgrade_em_home = upgrade_em_home;
	}
	public static String getCustom_conf_location() {
		return custom_conf_location;
	}
	public static void setCustom_conf_location(String custom_conf_location) {
		Utils.custom_conf_location = custom_conf_location;
	}

	/*
	 * Get the location of file based on the file name provided for Windows
	 */
	
	public static String getFileLocationbasedonFileNameWindows(String fileName, String em_home){		
		
		try{
			if(new File(em_home+File.separator+fileName).exists())
				return em_home;
			else if(new File(em_home+File.separator+"bin"+File.separator+fileName).exists())
				return em_home+File.separator+"bin";
			else if(new File(em_home+File.separator+"config"+File.separator+fileName).exists())
				return em_home+File.separator+"config";
			else if(new File(em_home+File.separator+"config"+File.separator+"esapi"+fileName).exists())
				return em_home+File.separator+"config"+File.separator+"esapi";
			else if(new File(em_home+File.separator+"config"+File.separator+"internal"+File.separator+"server"+File.separator+fileName).exists())
				return em_home+File.separator+"config"+File.separator+"internal"+File.separator+"server";
			else
				return getCustom_conf_location()+File.separator+fileName;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*
	 * Get the location of file based on the file name provided for LINUX or UNIX server
	 * 
	 * 
	 */
	

	public static String getFileLocationbasedonFileNameLinux(String fileName, String em_home){
		
		System.out.println("Home path is : "+em_home);
		ArrayList <String> al = new ArrayList<String>();
		
		try{			
			
			String cmd1= "ls " +em_home;	
			String cmd2= "ls " +em_home +"/"+ "bin";
			String cmd3= "ls " +em_home +"/"+ "config";
			String cmd4= "ls " +em_home +"/"+ "config" +"/"+ "esapi";
			String cmd5= "ls " +em_home +"/"+ "config" +"/"+ "internal"+"/"+ "server";
			
			String [] commands = {cmd1, cmd2, cmd3, cmd4, cmd5};
						
			for (int i=0; i<commands.length; i++) {
				
				al = LinuxUtils.getResult(commands[i]);
				
			if (al.contains(fileName) && commands[i].endsWith(em_home)) {
				    System.out.println("Returned path: "+em_home);
					return em_home;					
				} else if (al.contains(fileName) && commands[i].endsWith("bin")) {
				    System.out.println("Returned path: "+em_home +"/"+ "bin");
					return em_home +"/"+ "bin";						
				} else if (al.contains(fileName) && commands[i].endsWith("config")) {
				    System.out.println("Returned path: "+em_home +"/"+ "config");
					return em_home +"/"+ "config";						
				} else if (al.contains(fileName) && commands[i].endsWith("esapi")) {
				    System.out.println("Returned path: "+em_home +"/"+ "config" +"/"+ "esapi");
					return em_home +"/"+ "config" +"/"+ "esapi";					
				} else if (al.contains(fileName) && commands[i].endsWith("server")) {
				    System.out.println("Returned path: "+em_home +"/"+ "config" +"/"+ "internal"+"/"+ "server");
					return em_home +"/"+ "config" +"/"+ "internal"+"/"+ "server";					
				} 
								
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "INVALID_PATH"+"/"+fileName;
	}
	

}
