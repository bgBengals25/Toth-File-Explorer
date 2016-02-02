package me.pt.tfe.file.res;

import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconGrabber {

	private String icon_extension;
	private String icon_type;
	
	public IconGrabber(){
		
		icon_type = "ico";
		icon_extension = ".ico";
	}
	
	public IconGrabber(String icon_ext_without_dot){
		
		icon_type = icon_ext_without_dot;
		icon_extension = "."+icon_ext_without_dot;
	}
	
	public Icon getIcon(String name) throws IOException{
		
		System.out.println();
		Icon icon = new ImageIcon(new File("return10.png").getCanonicalPath());
		
		return icon;
	}
	
	public ImageIcon getImageIcon(String name){
		
		ImageIcon imageIcon = new ImageIcon("/Toth File Explorer/res/"+icon_type+"/"+name+icon_extension);
		
		return imageIcon;
	}
}
