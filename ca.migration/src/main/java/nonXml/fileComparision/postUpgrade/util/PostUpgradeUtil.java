package nonXml.fileComparision.postUpgrade.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class PostUpgradeUtil {
	
	    private static final Logger LOGGER = LoggerFactory.getLogger(PostUpgradeUtil.class);	
	    static Properties prop_base = null;
	    static Properties prop_upgrade= null;
	    static Properties prop_fresh= null;
	    static InputStream input_base = null;
	    static InputStream input_upgrade = null;
	    static InputStream input_fresh = null;	
	    static BufferedReader	br = null;

	public boolean unchangedPropComparison (String fileName, File baseFile, File upgradeFile) {		
		
		Map<String, String> propMap = new HashMap<String, String>();
		int count=0;
		boolean blResult = false;		
		
		try {
			prop_base = new Properties();
			prop_upgrade = new Properties();
			input_base = new FileInputStream (baseFile);
			input_upgrade = new FileInputStream (upgradeFile);			
			
			prop_base.load(input_base);
			
			Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
			
			while (itr.hasNext()) {
				Entry<Object, Object> propEntry = itr.next();				
				propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString()+ "@@@@@null");				
			}
			input_base.close();
			
			prop_upgrade.load(input_upgrade);
			itr = prop_upgrade.entrySet().iterator();
			
			while (itr.hasNext()) {
				
				Entry<Object, Object> propEntry = itr.next();
				String key = propEntry.getKey().toString();
				String propVal = propEntry.getValue().toString();
				
				if(propMap.containsKey(key)){
					String mapValue = propMap.get(key);						
					if(propVal.equalsIgnoreCase(mapValue.replace("@@@@@null",""))){
						blResult = true;
						count = count +1;							
					} else {
						LOGGER.info("The Property value "+mapValue.replace("@@@@@null","")+  " of Key "+key +" does not match with "+propVal);
						return blResult = false;
					}
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();		
			
		}finally {
			if (input_base != null) {
				try {
					input_base.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input_upgrade != null) {
				try {
					input_upgrade.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.info("Comparison result is : "+blResult);
		return blResult;

	}
	
	//Rule#1
	public boolean changedPropDefaultValue (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName)) && 
				PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName))) {
			
			blResult = true;
			LOGGER.info("changedPropDefaultValue result returned : "+blResult);
			return blResult;		
			
		}
		LOGGER.info("changedPropDefaultValue result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#4
	public boolean changedPropBaseCVFreshDVUpgradeCV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (!PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName))) {
			
			blResult = PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName));
			LOGGER.info("changedPropDefaultValue result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("changedPropDefaultValue result returned : "+blResult);
		return blResult;		

	}
    
	//Rule#5
	public boolean changedPropBaseDVFreshNDVUpgradeNDV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (!PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName))) {
			
			blResult = PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName));
			LOGGER.info("changedPropDefaultValue result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("changedPropDefaultValue result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#3
	public boolean changedPropBaseNullFreshNDVUpgradeNDV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName) == null && PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName) != null ) {
			
			blResult = PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName));
			LOGGER.info("changedPropDefaultValue result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("changedPropDefaultValue result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#2
	public boolean changedPropBaseAddNewCVFreshNullUpgradeCV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName) != null && PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName) == null ) {
			
			blResult = PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName));
			LOGGER.info("changedPropDefaultValue result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("changedPropDefaultValue result returned : "+blResult);
		return blResult;		

	}
    
	//Rule#6
	public boolean changedPropBaseCommentCVFreshDVUpgradeCommentCV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyCommentedValue(baseFile, keyName) == true && PostUpgradeUtil.getFreshPropertyCommentedValue(freshFile, keyName) == false ) {
			
			blResult = (PostUpgradeUtil.getUpgradedPropertyCommentedValue(baseFile, keyName) == true);
			LOGGER.info("Commented the Property "+keyName+" successfully and hence the result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("Commented Property Value result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#7
	public boolean changedPropBaseCVFreshComemntedDVUpgradeCV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName) !=null && PostUpgradeUtil.getFreshPropertyCommentedValue(freshFile, keyName) == true ) {
			
			blResult = (PostUpgradeUtil.getUpgradePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getBasePropertyValue(baseFile, keyName)));
			LOGGER.info("Comemnted Property Value result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("Commented Property Value result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#8
	public boolean changedPropBaseComment1DVFreshComemnt2DVUpgradeComemnt2DV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		//Needs to be implemented
		LOGGER.info("Commented Property Value result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#9
	public boolean changedPropBaseHPCVFreshNULLUpgradeHPCV (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName) !=null && PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName) == null) {
			
			blResult = (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName)));
			LOGGER.info("Hidden Property Value result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("Hidden Property Value result returned : "+blResult);
		return blResult;		

	}
	
	//Rule#10
	public boolean changedPropBaseDPCVFreshNULLUpgradeNULL (String fileName, String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (PostUpgradeUtil.getBasePropertyValue(baseFile, keyName) !=null && PostUpgradeUtil.getFreshPropertyValue(freshFile, keyName) == null) {
			
			blResult = (null == PostUpgradeUtil.getUpgradePropertyValue(upgradeFile, keyName));
			LOGGER.info("Depreciated Property Value result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}
		LOGGER.info("Depreciated Property Value result returned : "+blResult);
		return blResult;		

	}
	
	
	/*
	 * Methods to get the property values based on the Key and 
	 * also it will check whether the particular property is commented or not.
	 */
	public static String getBasePropertyValue(File baseFile, String keyName) {
		Map <String, String> propMap = new HashMap<String, String>();
		String base_value = "";
		
		try {
			prop_base = new Properties();
			input_base = new FileInputStream (baseFile);			
			prop_base.load(input_base);
			
			Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> propEntry = itr.next();
				
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
			
	        base_value = propMap.get(keyName);				
			LOGGER.info("Default Property base Value is : "+base_value);
			return base_value;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (input_base != null) {
				try {
					input_base.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
		
		return base_value;
		
	}
	
	public static String getUpgradePropertyValue(File upgradeFile, String keyName) {
		Map <String, String> propMap = new HashMap<String, String>();
		String upgrade_value = "";
		
		try {
			prop_upgrade = new Properties();
			input_upgrade = new FileInputStream (upgradeFile);
			
			prop_upgrade.load(input_upgrade);
			Iterator<Entry<Object,Object>> itr = prop_upgrade.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> propEntry = itr.next();				
				propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());	
	
			}
			upgrade_value = propMap.get(keyName);
			LOGGER.info("Default Property upgraded Value is : "+upgrade_value);
			return upgrade_value;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (input_upgrade != null) {
				try {
					input_upgrade.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
		
		return upgrade_value;
		
	}
	
	public static String getFreshPropertyValue(File freshFile, String keyName) {
		Map <String, String> propMap = new HashMap<String, String>();
		String Fresh_value = "";
		
		try {
			
			prop_fresh = new Properties();
			input_fresh = new FileInputStream (freshFile);
			
			prop_fresh.load(input_fresh);
			Iterator<Entry<Object,Object>> itr = prop_fresh.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> propEntry = itr.next();				
				propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());	
		
			}
			
			Fresh_value = propMap.get(keyName);
			LOGGER.info("Default Property Fresh Value is : "+Fresh_value);
			return Fresh_value;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input_fresh != null) {
				try {
					input_fresh.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
		
		return Fresh_value;
		
	}	
	
	public static boolean getBasePropertyCommentedValue(File baseFile, String keyName) {
		
		
		boolean blresult = false;
		try{

			br = new BufferedReader(new FileReader(baseFile));
			String line = null;

			while((line = br.readLine()) != null){
				if(line.startsWith("#"+keyName)){
					blresult = true;
					break;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{

			try{
				br.close();
			}catch(Exception e){

			}

		}
		
		return blresult;	
		
	}
	
	public static boolean getUpgradedPropertyCommentedValue(File baseFile, String keyName) {
		
		
		boolean blresult = false;
		try{

			br = new BufferedReader(new FileReader(baseFile));
			String line = null;

			while((line = br.readLine()) != null){
				if(line.startsWith("#"+keyName)){
					blresult = true;
					break;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{

			try{
				br.close();
			}catch(Exception e){

			}

		}
		
		return blresult;	
		
	}
	
	public static boolean getFreshPropertyCommentedValue(File baseFile, String keyName) {
		
		
		boolean blresult = false;
		try{

			br = new BufferedReader(new FileReader(baseFile));
			String line = null;

			while((line = br.readLine()) != null){
				if(line.startsWith("#"+keyName)){
					blresult = true;
					break;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{

			try{
				br.close();
			}catch(Exception e){

			}

		}
		
		return blresult;	
		
	}


	
}
