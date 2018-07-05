package com.deltax.automation.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class CommonUtils {


	public static List<Map<String,String>> readToMap(String filePath, String offset) throws Exception {

		ICsvListReader listReader = null;
		
		List<String> customerList; 
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			listReader = new CsvListReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE);

			listReader.getHeader(true); 
			while( (customerList = listReader.read()) != null) {
				
				String apiHeader = customerList.get(0);
				Map<String, String> map = new HashMap<String, String>();
				if(apiHeader.equals(offset)){
					System.out.println(customerList.remove(0)+"Is the removed String");
					for(Iterator<String> listIt = customerList.iterator();listIt.hasNext();){
						String temp = listIt.next();
						if(!StringUtils.isBlank(temp)){
							String[] intList = temp.split(":",2);
							try{
								map.put(intList[0].trim(),intList[1].trim());	
							}catch(ArrayIndexOutOfBoundsException e){
								map.put(intList[0].trim(), "");
							}
						}
					}
					System.out.println("MAP "+map);
					list.add(map);
					
				}                
			}
			System.out.println("LIST OF MAPs "+list);


		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if( listReader != null ) {
				listReader.close();
			}
		}
		return list;
	}	


	public boolean checkDirectoryExist(String path) {
		
		File f = new File(path);		
		if(f.exists() && f.isDirectory()) {
			System.out.println("Directory " + path + " exists");
			return true;
		}
		else
			return false;
		
	}

	public boolean createDirectory(String path) {
		
		File f = new File(path);
		if(!checkDirectoryExist(path)) {
			if(f.mkdirs()) {
				System.out.println("Directory Created");
				return true;
			}
		}
		
		return false;
	}
	
}
