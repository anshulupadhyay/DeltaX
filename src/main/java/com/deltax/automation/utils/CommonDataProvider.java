package com.deltax.automation.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import static com.deltax.automation.utils.CommonUtils.readToMap;

public class CommonDataProvider {

	private static final Logger logger = Logger.getLogger(CommonDataProvider.class);
	/**
	 * Generic DataProvider method to be used across all the test modules
	 * It takes the annotation class instance of the calling test method and gets the input csv file
	 * Parses the csv file for key value pairs and invokes the test method with the parsed params 
	 * @param testMethod
	 * @return
	 */
	@DataProvider(name="CommonCreateDelete")
	public static Object[][] UserCreateData(Method testMethod){
		try{
			String relFilePath = testMethod.getAnnotation(DataProviderArguments.class).value()[0].split("=")[1];
			String offset = testMethod.getAnnotation(DataProviderArguments.class).value()[1].split("=")[1];
			String absCSVFile = CommonUtils.class.getClassLoader().getResource(relFilePath).getPath();
			List<Map<String,String>> lineList = readToMap(absCSVFile, offset);
			Object[][] result=new Object[lineList.size()][];
			int i=0;
			for(Map<String,String> s:lineList){
				result[i]=new Object[]{s};
				i++;
			}
			return result;
		}
		catch (Exception e) {
			logger.error("CSV file cannot be read : " + e);
			return null;
		}
	}
}
