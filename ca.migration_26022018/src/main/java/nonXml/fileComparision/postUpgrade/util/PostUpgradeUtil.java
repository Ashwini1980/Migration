package nonXml.fileComparision.postUpgrade.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	    
	    BufferedReader br1 = null;
	    BufferedReader br2 = null;
	    static Path path = null;
	    static List <String> lines = null;
	    
	//Compare 2 files and find out the difference which includes comments 	    
	public boolean unchangedPropComparison (File baseFile, File upgradeFile) {
		
		boolean blResult = false;
		
		try {

		     String sCurrentLine;
		     
		     List<String> list1 = new ArrayList<String>();
		     List<String> list2 = new ArrayList<String>();
		     
		     br1 = new BufferedReader(new FileReader(baseFile));
		     br2 = new BufferedReader(new FileReader(upgradeFile));
			 
		     while ((sCurrentLine = br1.readLine()) != null) {
		         list1.add(sCurrentLine);
		     }

		     while ((sCurrentLine = br2.readLine()) != null) {
		         list2.add(sCurrentLine);
		     }
		     
		     List<String> tmpList = new ArrayList<String>(list1);
		     
		     tmpList.removeAll(list2);
		     
		     if (tmpList.size() == 0) {
		    
		    	 LOGGER.info("Both file are Same");
		    	 return blResult = true;
		    	 
		     } else {
		    	 
		    	 LOGGER.info("====Both file contents are NOT Same====");
		    	 LOGGER.info("=======================");
		    	 
		    	 LOGGER.info("content from "+baseFile+" which are NOT there in "+upgradeFile);		         
		         for(int i=0;i<tmpList.size();i++){
		        	 LOGGER.info (tmpList.get(i));
		         }

		         LOGGER.info("content from "+upgradeFile+" which are NOT there in "+baseFile);

		         tmpList = list2;
		         tmpList.removeAll(list1);
		         for(int i=0;i<tmpList.size();i++){
		        	 LOGGER.info (tmpList.get(i));
		         }		    	 
		    	 
		     }
		     
		     br1.close();
		     br2.close();
			
		} catch (Exception e) {
			e.printStackTrace();		
			
		} finally {
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
	
	//Compare 2 files and find out the difference which does NOT include comments i.e. only compares key and Values
	public boolean unchangedPropComparisonUpdated (File baseFile, File upgradeFile) {		
		
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
	public boolean changedPropBaseComment1DVFreshComemnt2DVUpgradeComemnt2DV (String keyName, File baseFile, File upgradeFile, File freshFile) {	
		
		boolean blResult = false;
		
		if (!PostUpgradeUtil.getNewCommentBasePropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getNewCommentFreshPropertyValue(freshFile, keyName))) {
			
			blResult = (PostUpgradeUtil.getNewCommentFreshPropertyValue(baseFile, keyName).equals(PostUpgradeUtil.getNewCommentUpgradedPropertyValue(upgradeFile, keyName)) );
			LOGGER.info("Commented Property Value result returned : "+blResult);
			Assert.assertEquals("After migration, Customized Value reflected successfully",true, blResult);
			return blResult;		
			
		}		
		
		LOGGER.info("Commented Property Value result returned (did not go to loop) : "+blResult);
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

	public static String getNewCommentBasePropertyValue(File baseFile, String keyName) {		
				
		try {
			
			String file = baseFile.toString();
			path = Paths.get(file);
			br = new BufferedReader (new FileReader (baseFile));
			
			String line;
			String newLine=null;
			int lineNum=0;
			
			lines = Files.readAllLines(path);
			
			while ((line = br.readLine()) != null) {
				
				lineNum++;
				
				if (line.startsWith(keyName)) {
					
					LOGGER.info ("The line num is " + lineNum);
					newLine = lines.get(lineNum-2);
					LOGGER.info ("The comment line is " + newLine);
					return newLine;
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

		return null;
	}
	
	public static String getNewCommentUpgradedPropertyValue(File upgradeFile, String keyName) {		
		
		try {
			String file = upgradeFile.toString();
			path = Paths.get(file);
			br = new BufferedReader (new FileReader (upgradeFile));
			
			String line;
			String newLine=null;
			int lineNum = 0;
			
			lines = Files.readAllLines(path);
			
			while ((line = br.readLine()) != null) {
				
				lineNum++;
				
				if (line.startsWith(keyName)) {
					
					LOGGER.info ("The line num is " + lineNum);
					newLine = lines.get(lineNum-2);
					LOGGER.info ("The comment line is " + newLine);
					return newLine;
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

		return null;
	}
	
	public static String getNewCommentFreshPropertyValue(File freshFile, String keyName) {		
		
		try {
			String file = freshFile.toString();
			path = Paths.get(file);
			br = new BufferedReader (new FileReader (freshFile));
			
			String line;
			String newLine=null;
			int lineNum = 0;
			
			lines = Files.readAllLines(path);
			
			while ((line = br.readLine()) != null) {
				
				lineNum++;
				
				if (line.startsWith(keyName)) {
					
					LOGGER.info ("The line num is " + lineNum);
					newLine = lines.get(lineNum-2);
					LOGGER.info ("The comment line is " + newLine);
					return newLine;
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

		return null;
	}
	
}
