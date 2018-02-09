package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static List<String> readFileByLine(String file) {
		//Create return
		List<String> ret = new ArrayList<String>();
		
		// Read file
        FileReader fr;
		try {
			fr = new FileReader(new File(file));
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while ( (line = br.readLine()) != null ) {
				line = line.trim();
				if(!line.isEmpty()){
					ret.add(line); // If the line it is not empty, then we save
				}				
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		
		return ret;
	}

}
