package nonXml.util;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	
	public String getProperty (String propertyName, String repositoryPath) {
		
		String propertyValue=null;
		Properties prop=null;
		FileInputStream fis = null;
		
		try {
			prop = new Properties();
			fis = new FileInputStream(repositoryPath);
			prop.load(fis);
			propertyValue = prop.getProperty(propertyName);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return propertyValue;
		
	}

}
