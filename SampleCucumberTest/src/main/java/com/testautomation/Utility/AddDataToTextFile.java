package com.testautomation.Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AddDataToTextFile {
	
	public static void  addDatatoTextFile(List<String> dataValues, String fileName) throws IOException {
		String path = System.getProperty("user.dir");
		String filePath = path + "\\resources\\"+fileName;
		FileWriter fw=new FileWriter(filePath); 
		for(String dataValue :dataValues)
	           fw.write(dataValue+"\n"); 
	           fw.close();    
		
	}

}
