package nonXml.fileComparision.testSuite;

import java.io.File;
import java.util.ArrayList;

import org.codehaus.plexus.util.Os;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import nonXml.common.LinuxUtils;
import nonXml.common.PreCheckFiles;
import nonXml.fileComparision.postUpgrade.util.PostUpgradeUtil;
import nonXml.testVariables.EnvironmentVariables;
import nonXml.testVariables.FileComparison;
import nonXml.util.Utils;


public class MigrationUtilFileComTests {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(MigrationUtilFileComTests.class); 
		private String testcaseId;	
		
		public static String OSW = "Windows";
		public static String OSL = "Linux";
		
		boolean isWindows = Os.isFamily(Os.FAMILY_WINDOWS);
		boolean isUnix = Os.isFamily(Os.FAMILY_UNIX);		
		
		PostUpgradeUtil postUpgradeUtil = new PostUpgradeUtil();
		
		
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "To compare any unchanged properties file on base and upgraded folder, all content should be same")
	  public void verify_ALM_UnchangedFileComaprision_properties(String OS) {
		  
			String [] listOfFiles = FileComparison.strUnchanged_Prop_Files_Fc.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "Unchanged_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());	    	   	    	    
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);	    	   	    	    
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties Unchanged file test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		result = postUpgradeUtil.unchangedPropComparison(base_file, upgrade_file);
	    	   	    		Assert.assertTrue("File comarison properties Unchanged file test", result.equals(true));    	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		} else if (OS.equalsIgnoreCase(OSL)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSL);
	    			
	    			//Connect to Linux Server 
    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    String base_file = base_loc+"/"+listOfFiles[i];
	    	   		    System.out.println("Base File is: "+base_file);
	    	   		    
	    	   		 if(!base_file.contains(Utils.getBase_em_home())){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence nothing to compare...");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false)); 
	    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameLinux (listOfFiles[i], Utils.getUpgrade_em_home());   
	    	   	    	    String upgrade_value = upgrade_loc+"/"+listOfFiles[i];	
	    	   	    	    System.out.println("Upgrade File is: "+upgrade_value);
	    	   	    	    
	    	   	    	 if(!upgrade_value.contains(Utils.getUpgrade_em_home())){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence nothing to compare...");
		    	    		 Assert.assertTrue("File comarison properties Unchanged file test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		 
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		result = postUpgradeUtil.unchangedPropComparisonLinux(base_file, upgrade_value);
	    	   	    		Assert.assertTrue("File comarison properties Unchanged file test", result.equals(true));    	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}  finally {
    	    			
    	    			LOGGER.info("Closing the LINUX/UNIX connection");
    	    			LinuxUtils.closeConnection();
    	    			
    	    		}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
	    	
		  
		  
		  
	  }
      
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = true, description ="If on Base and Current version have default property value then after migration, it should have Deafult Value.")
	  public void verify_ALM_FileComaprision_properties_Base_Current_Migration_DefaultValue(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_Base_Current_Migration_DefaultValue.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "Default_Prop_Value_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison Default properties file test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison Default properties file test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in both the upgraded and Fresh build, hence going to compare the content...");	    	   	    		
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getDefaultValuesPropValues();
	    	   	    			    	   	    		
	    	   	    		if (prop.size() >0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {	    	   	    				
	    	    	   	    		
	    	    	   	    		result = postUpgradeUtil.changedPropDefaultValue(FileComparison.strChanged_File_Name_Base_Current_Migration_DefaultValue, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);	
	    	    	   	    		Assert.assertTrue("Default value comarison test default result", result.equals(true));     	   	    				
	    	   	    				
	    	   	    			}
	    	   	    			
	    	   	    		}
 	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		} else if (OS.equalsIgnoreCase(OSL)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSL);
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
	    	
		  
		  
	  }
	        
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has CV, Current has DV/NDV then after migration it should have Customized Value(CV).")
	  public void verify_ALM_FileComaprision_properties_BaseCV_CurrentDVNDV_Migration_CV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentDVNDV_Migration_CV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseCVCurrentDVNDVMigrationCV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");   	   	    		
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseCVFreshDVNDVUpgradeCV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
			    	   	    		result = postUpgradeUtil.changedPropBaseCVFreshDVUpgradeCV(FileComparison.strChanged_File_Name_BaseCV_CurrentDVNDV_Migration_CV, 
			    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
			    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
			    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    			}
	    	   	    			

	    	   	    			
	    	   	    		}
		    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
	    	
		  
		  
	  }
	         
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has DV,  Current has NDV then after migration it should have NDV")
	  public void verify_ALM_FileComaprision_properties_BaseDV_CurrentNDV_Migration_NDV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDV_CurrentNDV_Migration_NDV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseDVCurentNDVMigrationNDV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseDVFreshNDVUpgradeNDV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseDVFreshNDVUpgradeNDV(FileComparison.strChanged_File_Name_BaseDV_CurrentNDV_Migration_NDV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
	  
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has NULL,  Current has NDV then after migration it should have NDV")
	  public void verify_ALM_FileComaprision_properties_BaseNULL_CurrentNDV_Migration_NDV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseNULL_CurrentNDV_Migration_NDV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseNullCurrentNDVMigrationNDV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseNULLFreshNDVUpgradeNDV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseNullFreshNDVUpgradeNDV(FileComparison.strChanged_File_Name_BaseNULL_CurrentNDV_Migration_NDV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
	  
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has AddNewCV,  Current has NULL then after migration it should have CV")
	  public void verify_ALM_FileComaprision_properties_BaseAddNewCV_CurrentNULL_Migration_AddNewCV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseAddNewCVCurrentNullMigrationCV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseAddNewCVFreshNULLUpgradeAddNewCV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseAddNewCVFreshNullUpgradeCV(FileComparison.strChanged_File_Name_BaseAddNewCV_CurrentNULL_Migration_CV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
	    
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has commented CV,  Current has DV then after migration it should have Commented CV")
	  public void verify_ALM_FileComaprision_properties_BaseCommentCV_CurrentDV_Migration_CommentCV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCommentCV_CurrentDV_Migration_CommentCV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseCommentCVCurrentDVMigrationCommentCV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseCommentCVFreshDVUpgradeCommentCV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseCommentCVFreshDVUpgradeCommentCV(FileComparison.strChanged_File_Name_BaseCommentCV_CurrentDV_Migration_CommentCV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }

      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has CV,  Current has CommentDV then after migration, it should be CV")
	  public void verify_ALM_FileComaprision_properties_BaseCV_CurrentCommentDV_Migration_CommentCV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentCommentDV_Migration_CV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseCVCurrentCommentDVMigrationCV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseCVFreshCommentDVUpgradeCV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseCVFreshComemntedDVUpgradeCV(FileComparison.strChanged_File_Name_BaseCV_CurrentCommentDV_Migration_CV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Unchanged File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
      
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has Comment1 DV,  Current has Comment2 DV then after migration, it should be Commen2 DV")
	  public void verify_ALM_FileComaprision_properties_BaseComment1DV_CurrentComment2DV_Migration_Comment2DV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCommentLine1DV_CurrentCommentLine2DV_Migration_CommentLine2DV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseComment1DVCurrentComment2DVMigrationComment2DV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in both the upgraded and Fresh build, hence going to compare the content...");	    	   	    			    	   	    		
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.
	    	    	   	    				changedPropBaseComment1DVFreshComemnt2DVUpgradeComemnt2DV(FileComparison.
	    	    	   	    						strChanged_prop_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV,
	    	    	   	    				base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result is:  "+result);
	    	    	   	    		Assert.assertTrue("Commented File comarison test default result", result.equals(true)); 
  	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
		  
		  
	  }

      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has HPCV,  Current has NULL then after migration, it should be HPCV")
	  public void verify_ALM_FileComaprision_properties_BaseHPCV_CurrentNULL_Migration_HPCV(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseHPCV_CurrentNULL_Migration_HPCV.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseHPCVCurrentNullMigrationHPCV_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison Hidden properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison Hidden properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseHPCVFreshNULLUpgradeHPCV();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseHPCVFreshNULLUpgradeHPCV(FileComparison.strChanged_File_Name_BaseHPCV_CurrentNULL_Migration_HPCV, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Hidden File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
	  
      @Parameters ("OS")
	  @Test(groups = { "BAT" }, enabled = false, description = "If base has DPDV,  Current has NULL then after migration, it should be NULL")
	  public void verify_ALM_FileComaprision_properties_BaseDPDV_CurrentNULL_Migration_NULL(String OS) {
		  
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDPDV_CurrentNULL_Migration_NULL.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	Boolean result=true;
	    	testcaseId = "BaseDPDVCurrentNullMigrationNull_Prop_File_Comparison";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {   
	    		
	    		if (OS.equalsIgnoreCase(OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(OSW);
	    			
	    			try {
	    				
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base em home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home());
	    	   		    
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]);
	    	   		    
	    	   		 if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("File comarison depreciated properties files test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade config path or not.");	    	   	    	    
	    	   	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	    	   	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	    	   	    	    
	    	   	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	    	   	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	    	   	    	    
	    	   	    	 if(!upgrade_file.exists() && !fresh_file.exists()){
	    	   	    		 
		    	    		 result = false;	
		    	    		 LOGGER.info("File DOES NOT exist in the Upgraded build, hence test case step Failed.");
		    	    		 Assert.assertTrue("File comarison depreciated properties files test", result.equals(false));     	   	    		 
	    	   	    		 
	    	   	    	 } else {
	    	   	    		LOGGER.info("File exists in the upgraded build, hence going to compare the content...");
	    	   	    		
	    	   	    		ArrayList <String> prop = FileComparison.getBaseDPDVFreshNULLUpgradeHPNULL();		    	   	    	
	    	   	    		if (prop.size() > 0) {
	    	   	    			
	    	   	    			for (int j=0; j <prop.size(); j++) {
	    	   	    				
	    	    	   	    		result = postUpgradeUtil.changedPropBaseDPCVFreshNULLUpgradeNULL(FileComparison.strChanged_File_Name_BaseDPDV_CurrentNULL_Migration_NULL, 
	    	    	   	    				prop.get(j).toString(), base_file, upgrade_file, fresh_file);
	    	    	   	    		LOGGER.info("The status result of "+prop.get(j) + " is "+result);
	    	    	   	    		Assert.assertTrue("Depreciated File comarison test default result", result.equals(true)); 
	    	   	    				
	    	   	    			}
	    	   	    		}
	    	   	    		
   	   	    		
	    	   	    		    	   	    		 
	    	   	    	 }      	    
	    	   	    	    
	    	    	    	
	    	    	    }
	    	   		    
	    				
	    			} catch (Exception e) {
	    				Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    			}
	    			
	    			
	    		}
	    }
	    	} else {
	    		
	    		Assert.assertTrue("You have not entered any File to compare content ", result.equals(false));
	    		
	    	}
		  
	  }
	  
	  
	  

}
