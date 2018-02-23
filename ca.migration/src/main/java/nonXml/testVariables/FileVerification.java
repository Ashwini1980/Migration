package nonXml.testVariables;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nonXml.util.PropertyReader;

public class FileVerification {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FileVerification.class);
	
	public static String OSW = "Windows";
	public static String OSL = "Linux";
	
	public static String getOSW() {
		return OSW;
	}

	public static void setOSW(String OSW) {
		FileVerification.OSW = OSW;
	}

	public static String getOSL() {
		return OSL;
	}

	public static void setOSL(String OSL) {
		FileVerification.OSL = OSL;
	}

	static PropertyReader prop = new PropertyReader();
	
	/*
	 * Variables for Windows system
	 */
	
	public static String strBase_Win_Em_Home = prop.getProperty("base_win_em_home", "Properties/TestData.properties");
	public static String strUpgade_Win_Em_Home = prop.getProperty("upgade_win_em_home", "Properties/TestData.properties");
	public static String strFresh_Win_Em_Home = prop.getProperty("fresh_win_em_home", "Properties/TestData.properties");
	
	
	/*
	 * Variables for Linux system
	 */
	
	public static String strBase_Ln_Em_Home = prop.getProperty("base_ln_em_home", "Properties/TestData.properties");
	public static String strUpgade_Ln_Em_Home = prop.getProperty("upgade_ln_em_home", "Properties/TestData.properties");
	public static String strFresh_Ln_Em_Home = prop.getProperty("fresh_ln_em_home", "Properties/TestData.properties");
	
   //Method to count the number of files
	
	public static int countFiles () {
		
		ArrayList<String> al = new ArrayList <String> ();
		if (OSW.equalsIgnoreCase(OSW)) {
			
			al.add(strBase_Win_Em_Home);
			al.add(strUpgade_Win_Em_Home);
			al.add(strFresh_Win_Em_Home);			
			al.removeAll(Arrays.asList(null,""));
		} else if (OSL.equalsIgnoreCase(OSL)) {
			al.add(strBase_Ln_Em_Home);
			al.add(strUpgade_Ln_Em_Home);
			al.add(strFresh_Ln_Em_Home);
			al.removeAll(Arrays.asList(null,""));
		}
		LOGGER.info("Returned the number of input files required by...."+al.size());		
		return al.size();
	}
	
	/*
	 * Variables for input files
	 */
	
	public static String strDepriciated_Prop_Files = prop.getProperty("depriciated_prop_files", "Properties/TestData.properties");
	public static String strNew_Prop_Files = prop.getProperty("new_prop_files", "Properties/TestData.properties");
	public static String strUnchanged_Prop_Files = prop.getProperty("unchanged_prop_files", "Properties/TestData.properties");
	

}
