package nonXml.testVariables;

import nonXml.util.PropertyReader;

public class EnvironmentVariables {
	
	static PropertyReader prop = new PropertyReader();
	
	/*
	 * Linux environment variables
	 */

	public static String strLinux_Host = prop.getProperty("linux_host", "Properties/Environment.properties");
	public static String strLinux_UserName = prop.getProperty("linux_userName", "Properties/Environment.properties");
	public static String strLinux_Password = prop.getProperty("linux_password", "Properties/Environment.properties");

	
}
