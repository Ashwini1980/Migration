package nonXml.testVariables;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nonXml.util.PropertyReader;

public class FileComparison {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FileVerification.class);
	static PropertyReader prop = new PropertyReader();
	
	static ArrayList <String> propValues = null;
	
	/*
	 * Variables for Window/LINUX base path setup
	 */
		
	
	/*
	 * Variables for input files
	 */
	
	public static String strUnchanged_Prop_Files_Fc = prop.getProperty("unchanged_prop_files_fc", "Properties/TestData_FileCom.properties");
	
	public static String strAdd_NewFile_Name = prop.getProperty("add_newFile_name", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_Base_Current_Migration_DefaultValue = prop.getProperty("changed_file_name_base_current_migration_defaultValue", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_Base_Current_Migration_DefaultValue = prop.getProperty("changed_prop_name_base_current_migration_defaultValue", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseCV_CurrentDVNDV_Migration_CV = prop.getProperty("changed_file_name_baseCV_currentDVNDV_migration_CV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseCV_CurrentDVNDV_Migration_CV = prop.getProperty("changed_prop_name_baseCV_currentDVNDV_migration_CV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseDV_CurrentNDV_Migration_NDV = prop.getProperty("changed_file_name_baseDV_currentNDV_migration_NDV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseDV_CurrentNDV_Migration_NDV = prop.getProperty("changed_prop_name_baseDV_currentNDV_migration_NDV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseNULL_CurrentNDV_Migration_NDV = prop.getProperty("changed_file_name_baseNULL_currentNDV_migration_NDV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseNULL_CurrentNDV_Migration_NDV = prop.getProperty("changed_prop_name_baseNULL_currentNDV_migration_NDV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseAddNewCV_CurrentNULL_Migration_CV = prop.getProperty("changed_file_name_baseAddNewCV_currentNULL_migration_CV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseAddNewCV_CurrentNULL_Migration_CV = prop.getProperty("changed_prop_name_baseAddNewCV_currentNULL_migration_CV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseCommentCV_CurrentDV_Migration_CommentCV = prop.getProperty("changed_file_name_baseCommentCV_currentDV_migration_CommentCV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV = prop.getProperty("changed_prop_name_baseCommentCV_currentDV_migration_CommentCV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseCV_CurrentCommentDV_Migration_CV = prop.getProperty("changed_file_name_baseCV_currentCommentDV_migration_CV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseCV_CurrentCommentDV_Migration_CV = prop.getProperty("changed_prop_name_baseCV_currentCommentDV_migration_CV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseCommentLine1DV_CurrentCommentLine2DV_Migration_CommentLine2DV = prop.getProperty("changed_file_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV", "Properties/TestData_FileCom.properties");
	public static String strChanged_prop_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV = prop.getProperty("changed_prop_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseHPCV_CurrentNULL_Migration_HPCV = prop.getProperty("changed_file_name_baseHPCV_currentNULL_migration_HPCV", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseHPCV_CurrentNULL_Migration_HPCV = prop.getProperty("changed_prop_name_baseHPCV_currentNULL_migration_HPCV", "Properties/TestData_FileCom.properties");
	
	public static String strChanged_File_Name_BaseDPDV_CurrentNULL_Migration_NULL = prop.getProperty("changed_file_name_baseDPDV_currentNULL_migration_NULL", "Properties/TestData_FileCom.properties");
	public static String strChanged_Prop_Name_BaseDPDV_CurrentNULL_Migration_NULL = prop.getProperty("changed_prop_name_baseDPDV_currentNULL_migration_NULL", "Properties/TestData_FileCom.properties");
	
		
	
	public static ArrayList<String> getDefaultValuesPropValues () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_Base_Current_Migration_DefaultValue.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}

	public static ArrayList<String> getBaseCVFreshDVNDVUpgradeCV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseCV_CurrentDVNDV_Migration_CV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}
	
	public static ArrayList<String> getBaseDVFreshNDVUpgradeNDV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseDV_CurrentNDV_Migration_NDV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}
	
	public static ArrayList<String> getBaseNULLFreshNDVUpgradeNDV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseNULL_CurrentNDV_Migration_NDV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}
	
	public static ArrayList<String> getBaseAddNewCVFreshNULLUpgradeAddNewCV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseAddNewCV_CurrentNULL_Migration_CV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}

	public static ArrayList<String> getBaseCommentCVFreshDVUpgradeCommentCV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}
	
	public static ArrayList<String> getBaseCVFreshCommentDVUpgradeCV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseCV_CurrentCommentDV_Migration_CV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}	
	
	public static ArrayList<String> getBaseComment1DVFreshComment2DVUpgradeComment2DV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_prop_name_baseCommentLine1DV_currentCommentLine2DV_migration_CommentLine2DV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}	
	
	public static ArrayList<String> getBaseHPCVFreshNULLUpgradeHPCV () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseHPCV_CurrentNULL_Migration_HPCV.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}	
	
	public static ArrayList<String> getBaseDPDVFreshNULLUpgradeHPNULL () {
		
		LOGGER.info("Verify the list of file User entered/provided");	
		String [] listOfFiles = FileComparison.strChanged_Prop_Name_BaseDPDV_CurrentNULL_Migration_NULL.trim().split(",");
		
		if (!listOfFiles[0].isEmpty() && listOfFiles.length > 0) {			
			propValues = new ArrayList<String> ();
			
			for (int i=0; i<listOfFiles.length; i++) {				
				propValues.add(listOfFiles[i]);			
				
			}
			return propValues;
			
		}
		
		return propValues;
		
	}	
	
	
	
}
