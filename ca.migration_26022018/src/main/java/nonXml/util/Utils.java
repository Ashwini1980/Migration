package nonXml.util;

import java.io.File;
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
	 * Get the location of file based on the file name provided for linux
	 * 
	 * 
	 */
	
/*	public static String getFileLocationbasedonFileNameLinux(String fileName, String em_home){
		
		System.out.println("EM path is: "+em_home);		
		
		try{
			if(new File(em_home+"/"+fileName).exists())
				return em_home;
			else if(new File(em_home+File.separator+"bin"+File.separator+fileName).exists())
				return em_home+File.separator+"bin";
			else if(new File(em_home+"/"+"config"+"/"+fileName).exists())
				return em_home+"/"+"config";
			else if(new File(em_home+File.separator+"config"+File.separator+"esapi"+fileName).exists())
				return em_home+File.separator+"config"+File.separator+"esapi";
			else if(new File(em_home+File.separator+"config"+File.separator+"internal"+File.separator+"server"+File.separator+fileName).exists())
				return em_home+File.separator+"config"+File.separator+"internal"+File.separator+"server";
			else
				return getCustom_conf_location()+"/"+fileName;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
		
		
		
	}
	*/
	
	
	public static String getFileLocationbasedonFileNameLinux(String fileName, String em_home){
		
		System.out.println("Home path is : "+em_home);

		try{
			String cmd1= "ls " +em_home;	
			String cmd2= "ls " +em_home +"/"+ "bin";
			String cmd3= "ls " +em_home +"/"+ "config";
			String cmd4= "ls " +em_home +"/"+ "config" +"/"+ "esapi";
			String cmd5= "ls " +em_home +"/"+ "config" +"/"+ "internal"+"/"+ "server";
			
			String [] s = {cmd1, cmd2, cmd3, cmd4, cmd5};
		      
			File file = new File(fileName);
			System.out.println("File Name is: "+file);
			for (int i=0; i<s.length; i++) {
				String command = s[i]+"|"+ "grep -i " +fileName;
				System.out.println(command);
				LinuxUtils.setCommand(command);	
				
/*				if (LinuxUtils.getResult()==true && cmd1.endsWith(em_home) ) {
					return em_home;					
				} else if (LinuxUtils.getResult()==true && cmd2.endsWith("bin") ) {
					return em_home+"/"+"bin";
				}else if (LinuxUtils.getResult()==true && cmd3.endsWith("config")) {
					return em_home+"/"+"config";
				}else if (LinuxUtils.getResult()==true && cmd4 !=null) {
					return em_home+"/"+"config"+"/"+"esapi";
				}else if (LinuxUtils.getResult()==true && cmd5 !=null) {
					return em_home+"/"+"config"+"/"+"internal"+"/"+"server";
				} else if (LinuxUtils.getResult()==false ){
					return getCustom_conf_location()+"/"+fileName;
				}*/
								
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
}
