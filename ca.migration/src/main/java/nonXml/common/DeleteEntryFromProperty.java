package nonXml.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nonXml.fileComparision.preUpgrade.util.DefaultPropUtil;
import nonXml.testVariables.FileComparison;

public class DeleteEntryFromProperty {
	
	private static String op_updatePropBaseCommentCVCurrentDV = "updateProperty_BaseCommentCVCurrentDV";
	private static String op_updatePropBaseCVCurrentDV = "updateProperty_BaseCVCurrentDV";
	private static String op_updatePropBaseCVCurrentCommentDV = "updateProperty_BaseCVCurrentCommentDV";
	private static String op_updatePropBaseHPCVCurrentNULL = "updateProperty_BaseHPCVCurrentNULL";
	private static String op_updatePropBaseDPDVCurrentNULL = "updateProperty_BaseDPDVCurrentNULL";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteEntryFromProperty.class);
	
	static String delete_value ="";
	static int default_Value = 100;
	
/*	public static boolean deleteEntryFromProperty (File fileName) {
		
		boolean blResult = false;
		
		String delete_Value = FileComparison.strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV +"="+default_Value;		
				
        try {       	
           
            FileReader fr = new FileReader(fileName);
            String s;
            String totalStr = "";
            BufferedReader br = new BufferedReader(fr);

                while ((s = br.readLine()) != null) {
                    totalStr += s+"\r\n";;
                }
                totalStr = totalStr.replaceAll(delete_Value, "");
                FileWriter fw = new FileWriter(fileName);
            
                fw.write(totalStr);
                
                br.close();
                fw.close();
                LOGGER.info("Deleted the previous entry successfully");
                //return blResult=true;
        }
        
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
		
		
		return blResult;
		
	}*/
	
	public static boolean deleteExistingProperty (File fileName, String operation) {
		boolean blResult = false;	
	
		
        try {       	
           
            FileReader fr = new FileReader(fileName);
            String s;
            String totalStr = "";
            BufferedReader br = new BufferedReader(fr);

                while ((s = br.readLine()) != null) {
                    totalStr += s+"\r\n";;

                }
                
                if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentDV)) {
                	
                	Map <Object, Object> prop = DefaultPropUtil.getProperty(fileName, op_updatePropBaseCVCurrentDV);
                	
                	delete_value = FileComparison.strChanged_Prop_Name_BaseCV_CurrentDVNDV_Migration_CV+"="
							+prop.get(FileComparison.strChanged_Prop_Name_BaseCV_CurrentDVNDV_Migration_CV);
                	
                	System.out.println("Delete value is "+delete_value);
                	totalStr = totalStr.replaceAll(delete_value, "");
                } else if (operation.equalsIgnoreCase(op_updatePropBaseCommentCVCurrentDV)) {
                	
                	Map <Object, Object> prop = DefaultPropUtil.getProperty(fileName, op_updatePropBaseCommentCVCurrentDV);
                	
            		delete_value = FileComparison.strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV+"="
            				+prop.get(FileComparison.strChanged_Prop_Name_BaseCommentCV_CurrentDV_Migration_CommentCV);	
                	
                	System.out.println("Delete value is "+delete_value);
                	totalStr = totalStr.replaceAll(delete_value, "");
                	
                }  else if (operation.equalsIgnoreCase(op_updatePropBaseCVCurrentCommentDV)) {
                	
                	Map <Object, Object> prop = DefaultPropUtil.getProperty(fileName, op_updatePropBaseCVCurrentCommentDV);
                	
            		delete_value = FileComparison.strChanged_Prop_Name_BaseCV_CurrentCommentDV_Migration_CV+"="
            				+prop.get(FileComparison.strChanged_Prop_Name_BaseCV_CurrentCommentDV_Migration_CV);	
                	
                	System.out.println("Delete value is "+delete_value);
                	totalStr = totalStr.replaceAll(delete_value, "");
                	
                } else if (operation.equalsIgnoreCase(op_updatePropBaseHPCVCurrentNULL)) {
                	
                	Map <Object, Object> prop = DefaultPropUtil.getProperty(fileName, op_updatePropBaseHPCVCurrentNULL);
                	
            		delete_value = FileComparison.strChanged_Prop_Name_BaseHPCV_CurrentNULL_Migration_HPCV+"="
            				+prop.get(FileComparison.strChanged_Prop_Name_BaseHPCV_CurrentNULL_Migration_HPCV);	
                	
                	System.out.println("Delete value is "+delete_value);
                	totalStr = totalStr.replaceAll(delete_value, "");
                	
                } else if (operation.equalsIgnoreCase(op_updatePropBaseDPDVCurrentNULL)) {
                	
                	Map <Object, Object> prop = DefaultPropUtil.getProperty(fileName, op_updatePropBaseDPDVCurrentNULL);
                	
            		delete_value = FileComparison.strChanged_Prop_Name_BaseDPDV_CurrentNULL_Migration_NULL+"="
            				+prop.get(FileComparison.strChanged_Prop_Name_BaseDPDV_CurrentNULL_Migration_NULL);	
                	
                	System.out.println("Delete value is "+delete_value);
                	totalStr = totalStr.replaceAll(delete_value, "");
                	
                }
             
                FileWriter fw = new FileWriter(fileName);
            
                fw.write(totalStr);
                
                br.close();
                fw.close();
                LOGGER.info("Deleted the previous entry successfully");
                
        }
        
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
		
		
		return blResult;
}
	

	
}