package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import classes.Utils;

/**
 * Config implements the class which allow us to create a config file where we
 * save interesting data for the system (paths, data values...)
 * 
 * Values are saved in Config.conf file (text file) and it allow its
 * modification during the execution
 *
 */

public class Config {
	
	private final String fileConfig = new String("./Config/Config.conf");

	private String PathClassification = new String("RutaClasificacion");
	private String PathData = new String("RutaData");
	private String Batscope = new String("RutaBatScope");

	/**
	 * Obtain the required parameter
	 * 
	 * @param what
	 *            : required parameter
	 * @return value of the parameter
	 */
	private String get(String characteristic) {
		String ret = null;
		List<String> configurations = Utils.readFileByLine(fileConfig);
		
		for(String aux : configurations){
			if(aux.startsWith("#")){
				aux = aux.substring(1);
				String[] temp = aux.split("=");
				
				if(temp[0].trim().equals(characteristic.trim())){
					ret = temp[1].trim();
					break;
				}
			}
		}
		
		return ret;
	}

	
	/**
	 * To obtain the .bat path
	 * 
	 * @return path of the classification bat files
	 */
	public String getBatScope() {
		return get(Batscope);
	}

	
	/**
	 * To obtain the path where are the input files from the classification algorithm
	 * 
	 * @return path where are the input files from the classification algorithm
	 */
	public String getPathClassification() {
		return get(PathClassification);
	}
	
	
	/**
	 * To obtain the path where are the data files
	 * 
	 * @return path where are the data files
	 */
	public String getPathData() {
		return get(PathData);
	}

}
