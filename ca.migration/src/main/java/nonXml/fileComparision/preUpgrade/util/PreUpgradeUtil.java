package nonXml.fileComparision.preUpgrade.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import nonXml.common.DeleteEntryFromProperty;

public class PreUpgradeUtil {
	
	private static String op_addNewPropFile = "addNewPropFile";
	private static String op_updatePropBaseCommentCVCurrentDV = "updateProperty_BaseCommentCVCurrentDV";
	private static String op_updatePropBaseCVCurrentDV = "updateProperty_BaseCVCurrentDV";
	private static String op_updatePropBaseCVCurrentCommentDV = "updateProperty_BaseCVCurrentCommentDV";
	private static String op_updatePropBaseHPCVCurrentNULL = "updateProperty_BaseHPCVCurrentNULL";
	private static String op_updatePropBaseDPDVCurrentNULL = "updateProperty_BaseDPDVCurrentNULL";
	
	PrintWriter pw = null;
	
	//To add a new Property
	public void addNewProperty (File fileName, Map <Object, Object> prop) {
		
		Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
		Entry<Object, Object> propEntry = itr.next();		
		
		try {
			
			pw = new PrintWriter (new FileWriter (fileName, true));
			
			for (int i=0; i < prop.size(); i++) {
				
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
				
				pw.flush();
				
			}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
			
		}
	
	}
	
	//To Update existing Property
	public void updateProperty (File fileName) {
		
		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(fileName, op_updatePropBaseCVCurrentDV);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
	
		try {
			
			pw = new PrintWriter (new FileWriter(fileName, true));
			
			while (itr.hasNext()) {
				
				Map.Entry<Object, Object> propEntry = itr.next();
				DeleteEntryFromProperty.deleteExistingProperty(fileName, op_updatePropBaseCVCurrentDV);			
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
	
		}
	}
	
	public void updatePropertyBaseCVCurrentComment (File fileName) {
		
		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(fileName, op_updatePropBaseCVCurrentCommentDV);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
	
		try {
			
			pw = new PrintWriter (new FileWriter(fileName, true));
			
			while (itr.hasNext()) {
				
				Map.Entry<Object, Object> propEntry = itr.next();
				DeleteEntryFromProperty.deleteExistingProperty(fileName, op_updatePropBaseCVCurrentCommentDV);			
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
	
		}
	}
	
	public void updatePropertyBaseHPCVCurrentNULL (File fileName) {
		
		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(fileName, op_updatePropBaseHPCVCurrentNULL);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
	
		try {
			
			pw = new PrintWriter (new FileWriter(fileName, true));
			
			while (itr.hasNext()) {
				
				Map.Entry<Object, Object> propEntry = itr.next();
				DeleteEntryFromProperty.deleteExistingProperty(fileName, op_updatePropBaseHPCVCurrentNULL);			
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
	
		}
	}
	
	public void updatePropertyBaseDPDVCurrentNULL (File fileName) {
		
		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(fileName, op_updatePropBaseDPDVCurrentNULL);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
	
		try {
			
			pw = new PrintWriter (new FileWriter(fileName, true));
			
			while (itr.hasNext()) {
				
				Map.Entry<Object, Object> propEntry = itr.next();
				DeleteEntryFromProperty.deleteExistingProperty(fileName, op_updatePropBaseDPDVCurrentNULL);			
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
	
		}
	}
	
	//Comment the existing Property
	public void commentProperty (File fileName) {

		Map <Object, Object> prop = DefaultPropUtil.addUpdateProperty(fileName, op_updatePropBaseCommentCVCurrentDV);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
	
		try {
			
			pw = new PrintWriter (new FileWriter(fileName, true));
			
			while (itr.hasNext()) {
				
				Map.Entry<Object, Object> propEntry = itr.next();				
				DeleteEntryFromProperty.deleteExistingProperty(fileName, op_updatePropBaseCommentCVCurrentDV);				
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println("#"+propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
		
			
		}
	
	}
	
	//To add new File under a folder
	public void addNewFile (String fileName) {	
		
		File path = DefaultPropUtil.getFilePath();
		Map <Object, Object> prop = DefaultPropUtil.addFile(fileName, op_addNewPropFile, path);
		Iterator<Map.Entry<Object, Object>> itr = prop.entrySet().iterator();
		
		String rpath = path+File.separator+fileName;
	
		try {
			
			pw = new PrintWriter (new FileWriter(rpath, true));
			
			while (itr.hasNext()) {
			
				Map.Entry<Object, Object> propEntry = itr.next();
				System.out.println("The key and Value pair is "+propEntry.getKey()+"="+propEntry.getValue());
				pw.println();
				pw.println(propEntry.getKey()+"="+propEntry.getValue());
			
				pw.flush();	
			
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			pw.close();
			
		}
	
	}	

}
