package nonXml.fileModification.testSuite;

import java.io.File;
import java.util.Map;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import nonXml.common.PreCheckFiles;
import nonXml.fileComparision.preUpgrade.util.DefaultPropUtil;
import nonXml.fileComparision.preUpgrade.util.PreUpgradeMain;
import nonXml.fileExistence.testSuite.MigrationFileExistenceUtilTests;
import nonXml.testVariables.FileComparison;
import nonXml.util.Utils;

public class MigrationFileModificationUtilTests {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationFileModificationUtilTests.class); 
	private String testcaseId;	
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
    private static String op_updatePropBaseCommentLine1DVCurrentCommentLine2DV = "updateProperty_BaseCommentLine1DVCurrentCommentLine2DV"; 
    private static String op_addNewPropFile = "addNewPropFile";
    
    //tested working fine
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - Add a new Property into base file"
			+ "that property not present in Current directory, hence after migration it should be present in upgraded folder")	
	
	public void verify_ALM_preUpgrade_BaseAddNewCV_CurrentNull(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_AddNew_Prop_File";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - update an existing property into base file"
			+ "that property present in Current directory as default value, hence after migration it should have customized value.")	
	
	public void verify_ALM_preUpgrade_BaseCV_CurrentDVNDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentDVNDV_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCV_CurrentDVNDV_MigrationCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - Default property in base version and in the current version that "
			+ "property should have new default value then after migration it should have the new default value.")	
	
	public void verify_ALM_preUpgrade_BaseDV_CurrentNDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDV_CurrentNDV_Migration_NDV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseDV_CurrentNDV_MigrationNDV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base version is not present but in the current version that "
			+ "property should be added and have new default value then after migration it should have the new default value.")	
	
	public void verify_ALM_preUpgrade_BaseNULL_CurrentNDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseNULL_CurrentNDV_Migration_NDV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseNULL_CurrentNDV_MigrationNDV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base version is commented but in the current version that "
			+ "property should has default value after migration that property should have commented.")	
	
	public void verify_ALM_preUpgrade_BaseCommentCV_CurrentDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCommentCV_CurrentDV_Migration_CommentCV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCommentCV_CurrentDV_MigrationCommentCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	
	//tested working fine.
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base version is Customized but in the current version that "
			+ "property is commented after migration that property should have Customized value.")	
	
	public void verify_ALM_preUpgrade_BaseCV_CurrentCommentDV(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseCV_CurrentCommentDV_Migration_CV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseCV_CurrentCommentDV_MigrationCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base version is hidden property but in the current version that "
			+ "property is not present after migration that property should have HPCV.")	
	
	public void verify_ALM_preUpgrade_BaseHPCV_CurrentNULL(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseHPCV_CurrentNULL_Migration_HPCV.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseHPCV_CurrentNULL_MigrationHPCV";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base version is depreciated property but in the current version that "
			+ "property is not present after migration that property should not be present.")	
	
	public void verify_ALM_preUpgrade_BaseDPDV_CurrentNULL(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			String [] listOfFiles = FileComparison.strChanged_File_Name_BaseDPDV_CurrentNULL_Migration_NULL.trim().split(",");

			Boolean result=false;
	    	testcaseId = "Preupgrade_Prop_Files_BaseDPDV_CurrentNULL_MigrationNULL";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
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
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	
	//Need to implement this method. Not fully imepletemented yet...
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - One property in base "
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
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File DOES NOT exist in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("updating existing property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	        	    		LOGGER.info("Files exists in the base directory, hence going to update one of the depreciated property"); 		
	        	    		
	        	    		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(base_file,op_updatePropBaseCommentLine1DVCurrentCommentLine2DV );	        	    		
	        	    		result = PreUpgradeMain.updateExistingProperty(base_file, prop);
	        	    		LOGGER.info("Existing property updated Successfully");
	        	    		Assert.assertTrue("updating existing property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for adding new file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
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
	
	@Parameters("OS")	
	@Test (groups = { "preUpgrade" }, enabled = false, description = "ConfigUtility Merge feature - Adding a new file in Base version but that file not present in"
			+ "current build then after migration that newly added file should be present unde the specific directory")	
	
	public void verify_ALM_preUpgrade_AddNewFile_BaseVersion(String OS) {
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			LOGGER.info("Test Case execution started");	
			
			Boolean result=false;
	    	testcaseId = "Preupgrade_AddNewFile_in BaseVersion";	    	
  		
	    			if (OS.equalsIgnoreCase(MigrationFileModificationUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileModificationUtilTests.OSW);	    				
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +FileComparison.strAdd_NewFile_Name+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(FileComparison.strAdd_NewFile_Name, Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+FileComparison.strAdd_NewFile_Name); 
	    	   		
	    	    	    if(base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.error("File already exists in the Base build, hence cannot be proceed.");
	    	    		   Assert.assertTrue("Adding new property file test", result.equals(false));	 	    		   
	    	    		   
	    	    	    } else {
	    	    	    	
	        	    		LOGGER.info("Files DOES NOT exist in the base directory, hence going to create a new property file"); 
	        	    		
	        	    		base_file = new File (Utils.getBase_em_home()+File.separator+"config"+File.separator+"shutoff");
	        	    		Map <Object, Object> prop = DefaultPropUtil.addFile(FileComparison.strAdd_NewFile_Name, op_addNewPropFile, base_file);	        	    		
	        	    		result = PreUpgradeMain.addNewFile(FileComparison.strAdd_NewFile_Name, prop);
	        	    		LOGGER.info("New Property file updated Successfully under "+base_file);
	        	    		Assert.assertTrue("Adding new property file test", result.equals(true));
	        	    		
	    	    	    }
	    	    	    
		    	    	} 	catch(Exception e) {
			    	    	
		    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
		    	    		
		    	    		}
		    	    		LOGGER.info("Test Case execution ended for Adding new property file test.");
		    			
		    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
		    			
    			
		                 //Need to implement the code	    	    	
		    	    	
		    	 
		    		} 		
	    	
						
			} else {
				Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
			}
			
		}
	
	
}
