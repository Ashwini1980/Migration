package nonXml.fileComparision.preUpgrade.util;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreUpgradeMain {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreUpgradeMain.class); 
	
	
	public static boolean addedNewProperty (File fileName, Map<Object, Object> prop){
		PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		boolean blResult = true;
					
			if(null == fileName || !fileName.exists()){
				LOGGER.info("File not found, please check"+fileName);
				return false;
			}
			preUpgradeUtil.addNewProperty(fileName, prop);
			LOGGER.info("The new property added successfully in "+fileName);
			
		return blResult;
	}
	
	public static boolean updateExistingProperty (File fileName, Map<Object, Object> prop){
		PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		boolean blResult = true;
					
			if(null == fileName || !fileName.exists()){
				LOGGER.info("File not found, please check"+fileName);
				return false;
			}
			preUpgradeUtil.updateProperty(fileName);
			LOGGER.info("The properties updated successfully in "+fileName);
			
		return blResult;
	}
	
	public static boolean updateExistingPropertyBaseCVCurrentComment (File fileName, Map<Object, Object> prop){
		PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		boolean blResult = true;
					
			if(null == fileName || !fileName.exists()){
				LOGGER.info("File not found, please check"+fileName);
				return false;
			}
			preUpgradeUtil.updatePropertyBaseCVCurrentComment(fileName);
			LOGGER.info("The properties updated successfully in "+fileName);
			
		return blResult;
	}
	
	public static boolean updateExistingPropertyBaseHPCVCurrent (File fileName, Map<Object, Object> prop){
		PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		boolean blResult = true;
					
			if(null == fileName || !fileName.exists()){
				LOGGER.info("File not found, please check"+fileName);
				return false;
			}
			preUpgradeUtil.updatePropertyBaseHPCVCurrentNULL(fileName);
			LOGGER.info("The properties updated successfully in "+fileName);
			
		return blResult;
	}
	
	public static boolean updateExistingPropertyBaseDPDVCurrent (File fileName, Map<Object, Object> prop){
		PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		boolean blResult = true;
					
			if(null == fileName || !fileName.exists()){
				LOGGER.info("File not found, please check"+fileName);
				return false;
			}
			preUpgradeUtil.updatePropertyBaseDPDVCurrentNULL(fileName);
			LOGGER.info("The properties updated successfully in "+fileName);
			
		return blResult;
	}
	
	public static boolean addNewFile (String fileName, Map<Object, Object> prop){
		  PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		  boolean blResult = true;
		  System.out.println("New File is: "+fileName);
	    
		  preUpgradeUtil.addNewFile(fileName);
		  LOGGER.info("The new File "+fileName +" added successfully with default properties.");
			
		return blResult;
	}
	
	public static boolean commentProperty (File fileName, Map<Object, Object> prop){
		
		  PreUpgradeUtil preUpgradeUtil = new PreUpgradeUtil();
		  boolean blResult = true;
	    
		  preUpgradeUtil.commentProperty(fileName);
		  LOGGER.info("The property commented successfully in the file "+fileName);
			
		return blResult;
	}
	

}
