package com.deltax.automation.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileValidation {

	/**
	 * This method will check of file in the given base directory
	 * @param baseDirectory
	 * @param fileName
	 * @return
	 */
	public File getConfigFile(File baseDirectory, String className) {
		
		File returnVal = null;
		
		try {
			
			File[] allFiles = baseDirectory.listFiles();
			FileFilter fileFilterpart = new WildcardFileFilter(className + "Config.xml");
			File[] classFile = baseDirectory.listFiles(fileFilterpart);
			
			if(classFile.length > 0) {
				return classFile[0];
			}else {
				for (int i = 0; i < allFiles.length; i++) {
					if(allFiles[i].isDirectory()) {
						returnVal = getConfigFile(allFiles[i], className);
						if(returnVal!=null)
							break;
					}
				}
			}			
			
		}catch(Exception e) {
			e.getMessage();
		}
		
		return returnVal;
	}
}
