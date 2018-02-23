package nonXml.fileExistence.testSuite;

import java.io.File;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import nonXml.common.LinuxUtils;
import nonXml.common.PreCheckFiles;
import nonXml.testVariables.EnvironmentVariables;
import nonXml.testVariables.FileVerification;
import nonXml.util.Utils;


public class MigrationFileExistenceUtilTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationFileExistenceUtilTests.class); 
	private String testcaseId;	
	public static String OSW = "Windows";
	public static String OSL = "Linux"; 
	
	@Parameters("OS")	
	@Test (groups = { "BAT" }, enabled = false, description = "ConfigUtility Merge feature - Verify migration for file depreciated properties files")	
	
	public void verify_ALM_Depreciated_properties_Files(String OS) {
		
		//If OS other than Windows/Linux, fail it
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			String [] listOfFiles = FileVerification.strDepriciated_Prop_Files.trim().split(",");		
		 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	//Check file existence "depreciated properties" which should be present in base version but should not be present in upgraded or fresh installation directory .
	    	Boolean result=true;
	    	testcaseId = "Depriciated_Prop_Files";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    	for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    		if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSW)) {
	    			
	    			PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSW);
	    	    	   
	    	    	try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		    System.out.println(base_loc);
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = false;	
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence test case step Failed.");
	    	    		   Assert.assertTrue("Depreciated properties file test", result.equals(false));    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed.");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade/Fresh EM config path or not.");
	        	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	        	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	        	    	
	        	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	        	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	        	    	    if(upgrade_file.exists() && fresh_file.exists()){
	        	    		result = false;
	        	    		LOGGER.info("File exists either in the updated or fresh build, hence test case failed.");
	        	    		Assert.assertTrue("Depreciated properties File test", result.equals(false));        	    		
	        	    	    } 
	        	    	    LOGGER.info("Verified that File "+listOfFiles[i]+" is NOT exist either in upgraded OR fresh EM build");
	        	    	    Assert.assertTrue("Depreciated properties File test", result.equals(true));        	    	       	    	    	
	    	    	    }    	   		
	 
	    	    	} 	catch(Exception e) {
	    	    	
	    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    	    		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for depreciated properties file test.");
	    			
	    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
	    			
	    	    		PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSL);   
	    	    		//Connect to Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
	    			
	    	    		try {
	    				
	    				LOGGER.info("Verify the file"+FileVerification.strDepriciated_Prop_Files+"is present in the base EM home directory");
	    				String base_loc = Utils.getFileLocationbasedonFileNameLinux(FileVerification.strDepriciated_Prop_Files, Utils.getBase_em_home());
	    				System.out.println("Linux Base location is: "+base_loc);
	    				//String base_loc= LinuxUtils.setCommand("cd ");
	    				
	    	    		} catch(Exception e) {
	    	    	
	    	    		//Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for test case ID=456391");

	    			
	                 //Need to implement the code
	    	    	
	    	    	
	    	 
	    		} 		
	    		
	    		
	    	}
	    	
	    	}	 else {    		
	    		Assert.assertTrue("You have not entered any File to Check ", result.equals(false));
	    		    		
	    		}
		} else {
			Assert.assertTrue("You have not entered correct OS version, enter either Windows or Linux", false);
		}
		

   
	}

	@Parameters("OS")	
	@Test (groups = { "BAT" }, enabled = false, description = "ConfigUtility Merge feature - Verify migration for file Additonal properties files")	
	
	public void verify_ALM_Addtional_properties_Files(String OS) {	
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			String [] listOfFiles = FileVerification.strNew_Prop_Files.trim().split(",");	
			System.out.println("User ented: "+listOfFiles.length);		
	 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	//Check file existence "Additional files entered by user" and should be present in upgrade but should not present in base version.
	    	Boolean result=false;
	    	testcaseId = "Additional_Prop_Files";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.info("File exists in the Base build, hence nothing to be verified.");
	    	    		   Assert.assertTrue("Additional properties file test", result.equals(false));
	    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File DOES NOT exist in the Base build, hence test case step Passed and proceeding...");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade/Fresh EM config path or not.");
	        	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	        	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	        	    	
	        	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	        	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	        	    	    if(!upgrade_file.exists() && !fresh_file.exists()){
	        	    		result = true;
	        	    		LOGGER.info("Files DOES NOT exist on both the updated or fresh build, hence test case failed.");
	        	    		Assert.assertTrue("Additional properties file test", result.equals(false));        	    		
	        	    	    }
	        	    	    
	        	    	    LOGGER.info("Verified that File "+listOfFiles[i]+" exists both in upgraded OR fresh EM build");
	        	    	    Assert.assertTrue("Additional properties FIle test", result.equals(false));        	    	        	    	    	
	    	    	    }    	   		
	 
	    	    	} 	catch(Exception e) {
	    	    	
	    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    	    		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for Additional properties file test.");
	    			
	    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
	    			
	    	    		PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSL);   
	    	    		//Connect to Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
	    			
	    	    		try {
	    				
	    				LOGGER.info("Verify the file"+FileVerification.strDepriciated_Prop_Files+"is present in the base em home directory");
	    				String base_loc = Utils.getFileLocationbasedonFileNameLinux(FileVerification.strDepriciated_Prop_Files, Utils.getBase_em_home());
	    				System.out.println("Linux Base location is: "+base_loc);
	    				//String base_loc= LinuxUtils.setCommand("cd ");
	    				
	    	    		} catch(Exception e) {
	    	    	
	    	    		//Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for test case ID=456391");

	    			
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
	@Test (groups = { "BAT" }, enabled = false, description = "ConfigUtility Merge feature - Verify migration for file Unchanged properties files")	
	
	public void verify_ALM_Unchanged_properties_Files(String OS) {	
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			String [] listOfFiles = FileVerification.strUnchanged_Prop_Files.trim().split(",");	
			System.out.println("User entered: "+listOfFiles.length);		
	 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	//Check file existence "Unchanged files entered by user" and should be present in all versions".
	    	Boolean result=false;
	    	testcaseId = "Unchanged_Prop_Files";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(!base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.info("File DOES NOT exist in the Base build, hence failing this step");
	    	    		   Assert.assertTrue("Unchanged properties file test", result.equals(false));
	    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File exists in the Base build, hence test case step Passed and proceeding...");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade/Fresh EM config path or not.");
	        	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	        	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	        	    	
	        	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	        	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	        	    	    if(!upgrade_file.exists() && !fresh_file.exists()){
	        	    		result = false;
	        	    		LOGGER.info("Files DOES NOT exist either on updated or fresh build, hence test case failed.");
	        	    		Assert.assertTrue("Unchanged properties file test", result.equals(false));        	    		
	        	    	    }
	        	    	    result = true;
	        	    	    LOGGER.info("Verified that File "+listOfFiles[i]+" exists both in upgraded OR fresh EM build");
	        	    	    Assert.assertTrue("Unchanged properties file test", result.equals(true));        	    	        	    	    	
	    	    	    }    	   		
	 
	    	    	} 	catch(Exception e) {
	    	    	
	    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    	    		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for Unchanged properties file test.");
	    			
	    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
	    			
	    	    		PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSL);   
	    	    		//Connect to Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
	    			
	    	    		try {
	    				
	    				LOGGER.info("Verify the file"+FileVerification.strDepriciated_Prop_Files+"is present in the base em home directory");
	    				String base_loc = Utils.getFileLocationbasedonFileNameLinux(FileVerification.strDepriciated_Prop_Files, Utils.getBase_em_home());
	    				System.out.println("Linux Base location is: "+base_loc);
	    				//String base_loc= LinuxUtils.setCommand("cd ");
	    				
	    	    		} catch(Exception e) {
	    	    	
	    	    		//Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for test case ID=456391");

	    			
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

/*	@Parameters("OS")	
	@Test (groups = { "BAT" }, enabled = true, description = "ConfigUtility Merge feature - Verify migration for file Additonal properties files i.e.files not present in base version "
			+ "but present in Upgraded/Fresh version")	
	
	public void verify_ALM_Generic_File_Comparison(String OS) {	
		
		if (OS.equalsIgnoreCase(OSW) || OS.equalsIgnoreCase(OSL)) {
			
			String [] listOfFiles = FileVerification.strNew_Prop_Files.trim().split(",");	
			System.out.println("User ented: "+listOfFiles.length);		
	 	
	    	LOGGER.info("Test Case execution started");	
	    	
	    	//Check file existence "Additional files entered by user" and should be present in upgrade but should not present in base version.
	    	Boolean result=false;
	    	testcaseId = "Additional_Prop_Files";
	    	
	    	if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {
	    		
	    		for (int i=0; i<listOfFiles.length; i++) {    		
	    		
	    			if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSW)) {
	    			
	    				PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSW);
	    	    	   
	    				try{	    	
	    	   		
	    	   		    LOGGER.info("Verify the file " +listOfFiles[i]+" is present in the base EM home directory");
	    	   		    String base_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getBase_em_home()); 
	    	   		
	    	   		    File base_file = new File(base_loc+File.separator+listOfFiles[i]); 
	    	   		
	    	    	    if(base_file.exists()){
	    	    		   result = true;
	    	    		   LOGGER.info("File exists in the Base build, hence cannot be compared.");
	    	    		   Assert.assertTrue("Additional properties files test", result.equals(false));
	    	    		   
	    	    	    } else {
	    	    	    	
	    	    	    	LOGGER.info("File DOES NOT exist in the Base build, hence test case step Passed and proceeding...");
	    	   	    	    LOGGER.info("Verify the " +listOfFiles[i]+" is present in the upgrade/Fresh EM config path or not.");
	        	    	    String upgrade_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getUpgrade_em_home());
	        	    	    String fresh_loc = Utils.getFileLocationbasedonFileNameWindows(listOfFiles[i], Utils.getFresh_em_home());
	        	    	
	        	    	    File upgrade_file = new File(upgrade_loc+File.separator+listOfFiles[i]);
	        	    	    File fresh_file = new File(fresh_loc+File.separator+listOfFiles[i]); 
	        	    	    if(!upgrade_file.exists() && !fresh_file.exists()){
	        	    		result = false;
	        	    		LOGGER.info("Files DOES NOT exist either on updated or fresh build, hence test case failed.");
	        	    		Assert.assertTrue("Unchanged properties test", result.equals(false));        	    		
	        	    	    }
	        	    	    
	        	    	    LOGGER.info("Verified that File "+listOfFiles[i]+" exists both in upgraded OR fresh EM build");
	        	    	    Assert.assertTrue("Unchanged properties test", result.equals(true));        	    	        	    	    	
	    	    	    }    	   		
	 
	    	    	} 	catch(Exception e) {
	    	    	
	    	    		Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   
	    	    		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for depreciated properties files.");
	    			
	    		} 	else if (OS.equalsIgnoreCase(MigrationFileExistenceUtilTests.OSL)) {
	    			
	    	    		PreCheckFiles.preCheckFiles(MigrationFileExistenceUtilTests.OSL);   
	    	    		//Connect to Server 
	    	    		LinuxUtils.connectToLinux(EnvironmentVariables.strLinux_UserName, EnvironmentVariables.strLinux_Password, EnvironmentVariables.strLinux_Host);
	    			
	    	    		try {
	    				
	    				LOGGER.info("Verify the file"+FileVerification.strDepriciated_Prop_Files+"is present in the base em home directory");
	    				String base_loc = Utils.getFileLocationbasedonFileNameLinux(FileVerification.strDepriciated_Prop_Files, Utils.getBase_em_home());
	    				System.out.println("Linux Base location is: "+base_loc);
	    				//String base_loc= LinuxUtils.setCommand("cd ");
	    				
	    	    		} catch(Exception e) {
	    	    	
	    	    		//Assert.assertTrue(testcaseId + " failed because of the Exception "+e,false);   		
	    	    		}
	    	    		LOGGER.info("Test Case execution ended for test case ID=456391");

	    			
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
*/
	
}
