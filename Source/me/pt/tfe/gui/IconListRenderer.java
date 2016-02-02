package me.pt.tfe.gui;

import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class IconListRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 2947188655368503673L;

	@SuppressWarnings("unused")
	private Map<Object, ImageIcon> icons = null;
	
	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		
		return null;	
	}
}
