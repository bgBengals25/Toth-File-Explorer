package me.pt.tfe.file;

import java.io.File;
import java.io.IOException;

public class FileActions {

	public static void renameFile(String oldPath, String newPath) throws IOException{
		
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		
		if(newFile.exists())
			throw new IOException("File Exists!");
		
		if(!(oldFile.renameTo(newFile)))
			throw new IOException("Failed to rename file!");
	}
}
