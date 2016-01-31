/*
 * PropertyParser.java
 * 
 * Created by Peter Toth
 * on 1/30/2016
 */

package me.pt.tfe.file.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyParser {

	private InputStream inputStream;
	private String propFileName;
	
	public PropertyParser(String propFileName){
		this.propFileName = propFileName;
	}
	
	public String[] getPropValues(String[] vals) {
		
		try{
			Properties prop = new Properties();
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if(inputStream != null){
				prop.load(inputStream);
			}else{
				throw new FileNotFoundException("Property file could not be found in classpath: "+propFileName);
			}
			
			for (int i = 0; i < vals.length; i++){
				vals[i] = prop.getProperty(vals[i]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return vals;
	}
	
	public void setPropValue(String propName, String propValue) {
		OutputStream out = null;
		try {

            Properties props = new Properties();

            File f = new File(getClass().getResource(propFileName).getFile());
            if(f.exists()){

                props.load(new FileReader(f));
                //Change your values here
                props.setProperty(propName, propValue);
            }
            else{
                f.createNewFile();
            }

            out = new FileOutputStream( f );
            props.store(out, "Set property: "+propName+" -> "+propValue);

       }
        catch (Exception e ) {
            e.printStackTrace();
        }
        finally{

            if(out != null){

                try {
                    out.close();
                } 
                catch (IOException ex) {

                    System.out.println("IOException: Could not close output stream -> " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
	}
}
