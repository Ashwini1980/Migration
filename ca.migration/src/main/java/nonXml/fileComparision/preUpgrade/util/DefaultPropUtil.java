package nonXml.fileComparision.preUpgrade.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nonXml.common.LinuxUtils;
import nonXml.testVariables.FileComparison;
import nonXml.util.Utils;

/*
 * To set default values
 */

public class DefaultPropUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropUtil.class); 
    static Properties prop_base = null;
    static InputStream input_base = null;
    static Map <Object, Object> propMap = null;
	public static String OSW = "Windows";
	public static String OSL = "Linux";
    
    private static String op_addProp = "addProperty";
    private static String op_updatePropBaseCVCurrentDV = "updateProperty_BaseCVCurrentDV";
    private static String op_updatePropBaseDVCurrentNDV = "updateProperty_BaseDVCurrentNDV";
    private static String op_updatePropBaseNULLCurrentNDV = "updateProperty_BaseNULLCurrentNDV";
    private static String op_updatePropBaseCommentCVCurrentDV = "updateProperty_BaseCommentCVCurrentDV";
    private static String op_updatePropBaseCVCurrentCommentDV = "updateProperty_BaseCVCurrentCommentDV";
    private static String op_updatePropBaseHPCVCurrentNULL = "updateProperty_BaseHPCVCurrentNULL";
    private static String op_updatePropBaseDPDVCurrentNULL = "updateProperty_BaseDPDVCurrentNULL";
    //private static String op_updatePropBaseCommentLine1DVCurrentCommentLine2DV = "updateProperty_BaseCommentLine1DVCurrentCommentLine2DV"; 
    private static String op_addNewPropFile = "addNewPropFile";
    
    static ArrayList <String> al = null;
    
    
    
	//Add a new property the base file and that file exists
	public static Map <Object, Object> addUpdateProperty (File fileName, String operation) {
		
		propMap = new HashMap<Object, Object>();
		LOGGER.info("You have entered the file "+fileName+ " to add/update a new/existing property");
		
		int default_Value = 100;
		
		try {
			    prop_base = new Properties();
		        input_base = new FileInputStream (fileName);			
		        prop_base.load(input_base);
		        
			    if(null == fileName || !fileName.exists()){	
					LOGGER.info("File not found, please check"+fileName);
					return null;	
					
			    } else if (operation.equalsIgnoreCase(op_addProp)) {			    	
					
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
		 	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentDV)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseCV_CurrentDVNDV_Migration_CV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;			    	
			    	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseDVCurrentNDV)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseDV_CurrentNDV_Migration_NDV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;	    	
			    	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseNULLCurrentNDV)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseNULL_CurrentNDV_Migration_NDV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
			    	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseCommentCVCurrentDV)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
			    	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentCommentDV)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseCV_CurrentCommentDV_Migration_CV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
				    
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseHPCVCurrentNULL)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseHPCV_CurrentNULL_Migration_HPCV.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
			    	
			    } else if (operation.equalsIgnoreCase(op_updatePropBaseDPDVCurrentNULL)) {
			    	
			    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseDPDV_CurrentNULL_Migration_NULL.trim().split(",");
			    	
				    for (int i=0; i< listOfProps.length; i++ ) {				    	
					    propMap.put(listOfProps[i], default_Value);
					    default_Value = default_Value + 100;
				    }
				    
				    return propMap;
			    	
			    }
			    
			    
			    

		} catch (IOException e) {
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
		
		return propMap;
		
	}
	
	//Add a new File under any directory
	
	public static Map <Object, Object> addFile (String fileName, String operation, File path) {
		
		propMap = new HashMap<Object, Object>();
		LOGGER.info("You have entered the file "+fileName+ " to add a new property");		
		
		int default_Value = 100;
		String default_Key = "ca.com.addNewFile";
	
		try {
			
			File newFile = new File (path+File.separator+fileName);
			
			if(null == newFile || !newFile.exists()) {
				LOGGER.info("The file " +fileName+" is not present, hence creating a new File");
				newFile.createNewFile();	
			}
			
		    prop_base = new Properties();
	        input_base = new FileInputStream (newFile);			
	        prop_base.load(input_base);
	        
		    if(operation.equalsIgnoreCase(op_addNewPropFile)){	
			
					propMap.put(default_Key, default_Value);	
					LOGGER.info("The default properties added successfully.");;
					return propMap;
					
			    } else {
			    	LOGGER.info("The file" +newFile+" is already present, please check your file details");
			    	Assert.assertTrue(false);
			    }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propMap;
		
	}
	
	//To get the path to add a new property file
	
	public static File getFilePath() {
		
		File filePath=null;
		
		try{	   		   		    
   		      		
   		    filePath = new File (Utils.getBase_em_home()+File.separator+"config"+File.separator+"shutoff");    		    
   		    
   		    return filePath;
   		    
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		return filePath;
	}
	
	public static Map <Object, Object> getProperty (File fileName, String operation) {
		propMap = new HashMap<Object, Object>();
		
		try {
		    prop_base = new Properties();
	        input_base = new FileInputStream (fileName);			
	        prop_base.load(input_base);
	        
	        if(null == fileName || !fileName.exists()){	
				LOGGER.info("File not found, please check"+fileName);
				return null;	
				
		    } else if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentDV)) {	
		    	
		    	Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
				while(itr.hasNext()){
					Entry<Object, Object> propEntry = itr.next();
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
				
				return propMap;	    	
		    	
		    } else if (operation.equalsIgnoreCase(op_updatePropBaseCommentCVCurrentDV)) {
		    	
		    	Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
				while(itr.hasNext()){
					Entry<Object, Object> propEntry = itr.next();
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
				
				return propMap;	 
				
		    }  else if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentCommentDV)) {
		    	
		    	Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
				while(itr.hasNext()){
					Entry<Object, Object> propEntry = itr.next();
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
				
				return propMap;	 
		    	
		    } else if (operation.equalsIgnoreCase(op_updatePropBaseHPCVCurrentNULL)) {
		    	
		    	Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
				while(itr.hasNext()){
					Entry<Object, Object> propEntry = itr.next();
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
				
				return propMap;	 		    	
		    	
		    } else if (operation.equalsIgnoreCase(op_updatePropBaseDPDVCurrentNULL)) {
		    	
		    	Iterator<Entry<Object,Object>> itr = prop_base.entrySet().iterator();
				while(itr.hasNext()){
					Entry<Object, Object> propEntry = itr.next();
					propMap.put(propEntry.getKey().toString(), propEntry.getValue().toString());
				}
				
				return propMap;
				
		    }
	        
	        
	        
		} catch (IOException e) {
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
		
		
		
		
		return propMap;
	}

	/*
	 * Linux methods to file operations starts here
	 */
	
	public static boolean addUpdatePropertyLinux (String fileName, String operation) {
		
		int default_Value = 100;
		boolean blResult = false;
		
		LOGGER.info("You have entered the file "+fileName+ " to add/update a new/existing property");
		
		if(null == fileName){	
			LOGGER.info("File not found, please check"+fileName);
			return false;	
			
	    } else if (operation.equalsIgnoreCase(op_addProp)) {			    	
			
	    	String [] listOfProps = FileComparison.strChanged_Prop_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");	    	
	    		    	
	    	for (int i=0; i< listOfProps.length; i++) {
	    		
	    		String gCommands = "grep -i "+listOfProps[i]+" "+fileName+" |wc -l";
	    		al = LinuxUtils.getResult(gCommands);
	    		LOGGER.info("The no of Keys in the file: "+al);
	    		
	    		//String command = "echo "+listOfProps[i]+"="+default_Value+" >> "+fileName+"";
	    		String command = "sed -i -e '$a\\"+listOfProps[i]+"="+default_Value+"' "+fileName+"";
	    		LOGGER.info("Command is: "+command);
	    		
	    		if (!al.get(0).equals("0") && al.size() > 0) {
	    			blResult = true;
	    			LOGGER.info("The Key is already present, hence not going to add anything...");
	    			Assert.assertFalse("The key is already present ", blResult);
	    			
	    		} else {
	    			
	    			LinuxUtils.getResult(command);
	    			al.clear();
	    			al = LinuxUtils.getResult(gCommands);
	    			
	    			if (!al.get(0).equals("0") && al.size() > 0) {
	    				blResult = true;
	    				LOGGER.info("The Key added successful....");
	    				Assert.assertTrue("The key added successfully ", blResult);
	    				return blResult;
	    			} else {
	    				LOGGER.info("The Key could NOT be added successful....");
	    				Assert.assertTrue("The key added successfully ", false);
	    			}
	    			
	    		}
	    		
	    	}
		
	    } else  {
	    	///Tomo will continue with other code for linux
	    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return blResult;
	}
	
	
	
	
}
