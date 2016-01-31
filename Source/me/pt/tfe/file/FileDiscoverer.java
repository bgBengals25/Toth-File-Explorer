/*
 * FileDiscoverer.java
 * 
 * Created by Peter Toth
 * on 1/29/2016
 */

package me.pt.tfe.file;

import java.io.File;
import java.util.ArrayList;

public class FileDiscoverer {

	public static String[] getDirectoryContents(String path, boolean showHidden){
		
		File f = new File(path);
		
		File[] files = f.listFiles();

		ArrayList<String> rfiles = new ArrayList<>();
		
		int i = 0;
		if (showHidden){
			for (File file : files){
				if(file.isDirectory()){
					rfiles.add(file.getName()+"/");
				}else{
					rfiles.add(file.getName());
				}
				i++;
			}
		}else{
			for (File file : files){
				if(file.isDirectory()){
					if(!file.getName().startsWith("."))
						rfiles.add(file.getName()+"/");
				}else{
					if(!file.getName().startsWith("."))
						rfiles.add(file.getName());
				}
				i++;
			}
		}
		
		String[] retArray = new String[rfiles.size()];
		
		for (i = 0; i<rfiles.size(); i++){
			retArray[i] = rfiles.get(i);
		}
		
		return retArray;
	}
	
	public static String getDirectoryOf(String path){
		
		String[] brokenPath = path.split("/");
		String[] newPathBroken = new String[brokenPath.length-1];
		for(int i = 0; i < newPathBroken.length; i++)
			newPathBroken[i] = brokenPath[i];
		String newPath = "";
		for(String s : newPathBroken)
			newPath += s+"/";
		
		return newPath;
	}
}
