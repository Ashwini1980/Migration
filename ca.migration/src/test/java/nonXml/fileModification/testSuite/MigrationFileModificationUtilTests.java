package nonXml.fileModification.testSuite;

import java.io.File;
import java.util.Map;

import org.codehaus.plexus.util.Os;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import nonXml.common.LinuxUtils;
import nonXml.common.PreCheckFiles;
import nonXml.fileComparision.preUpgrade.util.DefaultPropUtil;
import nonXml.fileComparision.preUpgrade.util.PreUpgradeMain;
import nonXml.testVariables.EnvironmentVariables;
import nonXml.testVariables.FileComparison;
import nonXml.util.Utils;

public class MigrationFileModificationUtilTests {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationFileModificationUtilTests.class); 
	private String testcaseId;	

	boolean isWindows = Os.isFamily(Os.FAMILY_WINDOWS);
	boolean isUnix = Os.isFamily(Os.FAMILY_UNIX);
	
	private static String OSW = "Windows";
	private static String OSL = "Linux";
	
	
    private static String op_addProp = "addProperty";
    private static String op_updatePropBaseCVCurrentDV = "updateProperty_BaseCVCurrentDV";
    private static String op_updatePropBaseDVCurrentNDV = "updateProperty_BaseDVCurrentNDV";
    private static String op_updatePropBaseNULLCurrentNDV = "updateProperty_BaseNULLCurrentNDV";
    private static String op_updatePropBaseCommentCVCurrentDV = "updateProperty_BaseCommentCVCurrentDV";
    private static String op_updatePropBaseCVCurrentCommentDV = "updateProperty_BaseCVCurrentCommentDV";
    private static String op_updatePropBaseHPCVCurrentNULL = "updateProperty_BaseHPCVCurrentNULL";
    private static String op_updatePropBaseDPDVCurrentNULL = "updateProperty_BaseDPDVCurrentNULL";
    private static String op_updatePropBaseCommentLine1DVCurrentCommentLine2DV = "updateProperty_BaseCommentLine1DVCurrentCommentLine2DV"; 
    private static String op_addNewPropFile = "addNewPropFile";
    
    //tested working fine

    @Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - Add a new Property into base file"
			+ "that property not present in Current directory, hence after migration it should be present in upgraded folder")	
	
	public void verify_ALM_preUpgrade_BaseAddNewCV_CurrentNull(String OS) {
		
		if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_AddNew_Prop_File";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot add any new Property under that file...");
	    	    		   Assert.assertTrue("Adding new property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to add a new property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_addProp);	        	    		
	        	    		result = PreUpgradeMain.addedNewProperty(base_file, prop);
	        	    		LOGGER.info("New Property Added Successfully");
	        	    		Assert.assertTrue("Adding new property files test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for adding new property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSL)) {
		    				
		    					PreCheckFiles.preCheckFiles(OSL);
		    					
			    	    		//Connect to Linux Server 
			    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
    			
		    			try{	    	
			    	   		
		    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
		    	   		    String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
		    	   		    
		    	   		    String base_value = base_loc+"/"+listOfFiles[i];
		    	   		    LOGGER.info ("Base file location is: "+base_value);
		    	   		    
		    				   if(!base_value.contains(Utils.getBase_em_home())){
			    	    		   result = true;	
			    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence cannot add any new Property under that file...");
			    	    		   Assert.assertTrue("Adding new property file test", result.equals(false));
			    	    		   
			    	    	    } else {
			    	    	    	
			    	    	    	LOGGER.info("Files exists in the base directory, hence going to add a new property");		    	    	    	
			        	    		     	    		
			        	    		result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_addProp );
			        	    		LOGGER.info("New Property Added Successfully");
			        	    		Assert.assertTrue("Adding new property files test", result.equals(true));
			    	    	
			    	    	    }
			    	    	
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			} finally {
	    	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    	    			
	    	    		}
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}

	//tested working fine
	
    @Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - update an existing property into base file"
			+ "that property present in Current directory as default value, hence after migration it should have customized value.")	
	
	public void verify_ALM_preUpgrade_BaseCV_CurrentDVNDV(String OS) {
		
		if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW) || OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentDVNDV_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCV_CurrentDVNDV_MigrationCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Updating existing property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseCVCurrentDV);	        	    		
	        	    		result = PreUpgradeMain.updateExistingProperty(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("Updating existing property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for updaing the existing property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSL)) {
		    			
    					PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseCVCurrentDV );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}
    	 
		    		} 		
    		
    		
	    		}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	//This test case is only for testing purpose, in real environment we do not need to run this test case.

	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - Default property in base version and in the current version that "
			+ "property should have new default value then after migration it should have the new default value.")	
	
	public void verify_ALM_preUpgrade_BaseDV_CurrentNDV() {
		
		if (isWindows || isUnix) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDV_CurrentNDV_Migration_NDV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseDV_CurrentNDV_MigrationNDV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (isWindows) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("New default property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseDVCurrentNDV);	        	    		
	        	    		result = PreUpgradeMain.updateExistingProperty(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("New default property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for updaing the existing property file test.");
		    			
		    		} 	else if (!isWindows) {
		    			
    			
		                 //Need to implement the code	    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	//This test case is only for testing purpose, in real environment we do not need to run this test case.

	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base version is not present but in the current version that "
			+ "property should be added and have new default value then after migration it should have the new default value.")	
	
	public void verify_ALM_preUpgrade_BaseNULL_CurrentNDV() {
		
		if (isWindows || isUnix) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseNULL_CurrentNDV_Migration_NDV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseNULL_CurrentNDV_MigrationNDV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (isWindows) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("New default property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseNULLCurrentNDV);	        	    		
	        	    		result = PreUpgradeMain.updateExistingProperty(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("New default property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for updaing the existing property file test.");
		    			
		    		} 	else if (!isWindows) {
		    			
    			
		                 //Need to implement the code	    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
   	
	//tested working fine
	
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base version is commented but in the current version that "
			+ "property should has default value after migration that property should have commented.")	
	
	public void verify_ALM_preUpgrade_BaseCommentCV_CurrentDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCommentCV_CurrentDV_Migration_CommentCV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCommentCV_CurrentDV_MigrationCommentCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Update existing property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseCommentCVCurrentDV);	        	    		
	        	    		result = PreUpgradeMain.commentProperty(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("Update existing property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for updaing the existing property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {
		    			
    			
    					PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseCommentCVCurrentDV );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}
		    		}
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	//tested working fine.
	
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base version is Customized but in the current version that "
			+ "property is commented after migration that property should have Customized value.")	
	
	public void verify_ALM_preUpgrade_BaseCV_CurrentCommentDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentCommentDV_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCV_CurrentCommentDV_MigrationCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Update existing property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseCVCurrentCommentDV);	        	    		
	        	    		result = PreUpgradeMain.updateExistingPropertyBaseCVCurrentComment(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("Update existing property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for updaing the existing property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {
		    			
    					PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseCVCurrentCommentDV );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}	    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	//tested working fine
	
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base version is hidden property but in the current version that "
			+ "property is not present after migration that property should have HPCV.")	
	
	public void verify_ALM_preUpgrade_BaseHPCV_CurrentNULL(String OS) {
		
		if (OS.equalsIgnoreCase(OSW)|| OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseHPCV_CurrentNULL_Migration_HPCV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseHPCV_CurrentNULL_MigrationHPCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Update hidden property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the hidden existing property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseHPCVCurrentNULL);	        	    		
	        	    		result = PreUpgradeMain.updateExistingPropertyBaseHPCVCurrent(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("Update hidden property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for Updating hidden property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {
		    			
		    			PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseHPCVCurrentNULL );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	//tested working fine
	
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base version is depreciated property but in the current version that "
			+ "property is not present after migration that property should not be present.")	
	
	public void verify_ALM_preUpgrade_BaseDPDV_CurrentNULL(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDPDV_CurrentNULL_Migration_NULL.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseDPDV_CurrentNULL_MigrationNULL";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Update depreciated property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the depreciated property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file, op_updatePropBaseDPDVCurrentNULL);	        	    		
	        	    		result = PreUpgradeMain.updateExistingPropertyBaseDPDVCurrent(base_file, prop);
	        	    		LOGGER.info("Property updated Successfully");
	        	    		Assert.assertTrue("Update depreciated property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for Updating depreciated property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {		    			
    			
		    			PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseDPDVCurrentNULL );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}    	    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	

	//TESTED working fine
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - One property in base "
			+ "version is default property and above that add one commentline say comment1 and but in the current version above the same property "
			+ "property, add another comment say Comment2 after migration comment2 should be populdated above that property.")	
	
	public void verify_ALM_preUpgrade_BaseCommentLine1DV_CurrentCommentLine2DV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCommentLine1DV_CurrentCommentLine2DV_Migration_CommentLine2DV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseDPDV_CurrentNULL_MigrationNULL";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("updating existing property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update add a comment for the said property"); 		
	        	    			        	    			        	    		
	        	    		result = PreUpgradeMain.addedNewComment(base_file, FileComparison.strChanged_prop_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV);
	        	    		LOGGER.info("Existing property updated Successfully");
	        	    		Assert.assertTrue("updating existing property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for adding new file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {
		    			
    			
		    			PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(!base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File DOES NOT exist in the Base build, hence cannot update any existing Property under that file...");
	    	    				Assert.assertTrue("Updating new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files exists in the base directory, hence going to update the existing property");		    	    	    	
	        	    		     	    		
	    	    				result = DefaultPropUtil.addUpdatePropertyLinux(base_value, op_updatePropBaseCommentLine1DVCurrentCommentLine2DV );
	    	    				LOGGER.info("Updating existing Property updated Successfully");
	    	    				Assert.assertTrue("Updating new property files test", result.equals(true));
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}    	    	    	
		    	    		    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	@Parameters ("OS")
	@Test (groups = { "preUpgrade" }, enabled = true, description = "ConfigUtility Merge feature - Adding a new file in Base version but that file not present in"
			+ "current build then after migration that newly added file should be present unde the specific directory")	
	
	public void verify_ALM_preUpgrade_AddNewFile_BaseVersion(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strAdd_NewFile_Name.trim().split(",");
			
			Boolean result=false;
	    	testcaseId = "Preupgrade_AddNewFile_in BaseVersion";	
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) { 
  		
	    			if (OS.equalsIgnoreCase(OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(OSW);	    				
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File already exists in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Adding new property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	    	    	    	
	        	    		LOGGER.info("Files DOES NOT exist in the base directory, hence going to create a new property file"); 
	        	    		
	        	    		base_file = new File (Utils.getBase_em_home()+File.separator+"config"+File.separator+"shutoff");
	        	    		Map <Object, Object> prop = DefaultPropUtil.addFile(listOfFiles[i], op_addNewPropFile, base_file);	        	    		
	        	    		result = PreUpgradeMain.addNewFile(listOfFiles[i], prop);
	        	    		LOGGER.info("New Property file updated Successfully under "+base_file);
	        	    		Assert.assertTrue("Adding new property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for Adding new property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(OSL)) {
		    			
    			
		    			PreCheckFiles.preCheckFiles(OSL);
    					
	    	    		//Connect to Linux Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
		
	    	    		try{	    	
	    	   		
	    	    			LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	    			String base_loc = Utils.getFileLocationbasedonFileNameLinux(listOfFiles[i], Utils.getBase_em_home());	 
    	   		    
	    	    			String base_value = base_loc+"/"+listOfFiles[i];
	    	    			LOGGER.info ("Base file location is: "+base_value);
    	   		    
	    	    			if(base_value.contains(Utils.getBase_em_home())){
	    	    				result = true;	
	    	    				LOGGER.info("File already exist in the Base build, hence cannot add the specified Property file under the directory...");
	    	    				Assert.assertTrue("Adding new property file test", result.equals(false));
	    	    		   
	    	    			} else {
	    	    	    	
	    	    				LOGGER.info("Files does NOT exist in the base directory, hence going to add the new property file");		    	    	    	
	    	    				String base_path = Utils.getBase_em_home()+"/"+"config"+"/"+"shutoff"; 	    		
	    	    				result = DefaultPropUtil.addFileInLinux(listOfFiles[i], op_addNewPropFile, base_path);
	    	    				
	    	    				Assert.assertTrue("Addinf new property files test", result.equals(true));
	    	    				LOGGER.info("Added new Property file Successfully");
	    	    	
	    	    			}
	    	    	
	    	    		} catch (Exception e) {
	    	    			e.printStackTrace();
	    	    		} finally {
	    			
	    	    			LOGGER.info("Closing the LINUX/UNIX connection");
	    	    			LinuxUtils.closeConnection();
	    			
	    	    		}    	    	    	
		    	    		    	    	
		    	    	
		    	 
		    		} 		
		    		
		    		
		    	}
		    	
		    	}	else {    		
		    		Assert.assertTrue("You have not entered any File to Check ", result.equals(true));
		    		    		
		    		}		
				
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}

	
}
