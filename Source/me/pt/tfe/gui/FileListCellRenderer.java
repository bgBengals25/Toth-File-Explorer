/*
 * FileListCellRenderer.java
 * 
 * Created by Peter Toth
 * on 2/2/2016
 */

package me.pt.tfe.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.metal.MetalIconFactory;

public class FileListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 7324139184267904837L;
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){

		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if(value.toString().endsWith("/")){
			label.setIcon(MetalIconFactory.getFileChooserNewFolderIcon());
		}else{
			label.setIcon(MetalIconFactory.getTreeLeafIcon()); // MetalIconFactory.getTreeLeafIcon()
		}
		
		return label;
	}
}