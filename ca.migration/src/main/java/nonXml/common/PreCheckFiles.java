package nonXml.common;

import org.codehaus.plexus.util.Os;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import nonXml.testVariables.FileVerification;
import nonXml.util.Utils;

public class PreCheckFiles {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreCheckFiles.class); 
	
	static boolean isWindows = Os.isFamily(Os.FAMILY_WINDOWS);
	
    @Test (alwaysRun=true, description = "Check whether user provides atleast 3 parameters or not")
    
	public static void preCheckFiles (boolean OS) {
    
		LOGGER.info("Start pre-upgrade tasks...");
		
		if (FileVerification.countFiles() <3) {
			LOGGER.info("ERROR: Please provide 3 property inputs and it seems you are passing less than 3 parameters, please try again...");
			return;
		}		    
			if (isWindows) {
					LOGGER.info("You have selected OS type as WINDOWS");
				    Utils.setBase_em_home(FileVerification.strBase_Win_Em_Home);
					Utils.setUpgrade_em_home(FileVerification.strUpgade_Win_Em_Home);
					Utils.setFresh_em_home(FileVerification.strFresh_Win_Em_Home);
				} else if (!isWindows) {
					LOGGER.info("You have selected OS type as LINUX/UNIX");
					Utils.setBase_em_home(FileVerification.strBase_Ln_Em_Home);
					Utils.setUpgrade_em_home(FileVerification.strUpgade_Ln_Em_Home);
					Utils.setFresh_em_home(FileVerification.strFresh_Ln_Em_Home);
				}			
			
			
			LOGGER.info("Pre-upgrade tasks are done");
			

	}

}
