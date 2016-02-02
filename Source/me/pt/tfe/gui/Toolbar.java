/*
 * Toolbar.java
 * 
 * Created by Peter Toth
 * on 1/29/2016
 */

package me.pt.tfe.gui;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import me.pt.tfe.Grid;

public class Toolbar extends JToolBar{

	private static final long serialVersionUID = 4349200480574845408L;
	
	public Toolbar(Grid g){
		
		this.setFloatable(false);
		
		setMargin(new Insets(5, 5, 5, 5));
		
		JButton upButton = new JButton();
		upButton.setToolTipText("Navigate up a directory");
		upButton.setIcon((Icon)new ImageIcon(new ImageIcon("res/return10.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		upButton.setSize(64, 64);
		upButton.setFocusable(false);
		upButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] splitPath = Grid.CURRENT_PATH.split("/");
				String[] chopPath = new String[splitPath.length-1];
				int l = splitPath.length-1;
				for (int i = 0; i<l; i++)
					chopPath[i] = splitPath[i];
				String rejoinedPath = "";
				boolean f = true;
				for (String s : chopPath){
					if (f){
						rejoinedPath += s;
						f = false;
					}else{
						rejoinedPath += "/"+s;
					}
				}
				rejoinedPath += "/";
				Grid.CURRENT_PATH = rejoinedPath;
				g.updateInterface();
			}
		});
		add(upButton);
		
		addSeparator();
		
		JButton homeButton = new JButton();
		homeButton.setToolTipText("Navigate to your home directory");
		homeButton.setIcon((Icon)new ImageIcon(new ImageIcon("res/home150.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		homeButton.setFocusable(false);
		homeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Grid.CURRENT_PATH = "/home/"+System.getProperty("user.name")+"/";
				g.updateInterface();
			}
		});
		add(homeButton);
		
		addSeparator();
		
		JButton rootButton = new JButton();
		rootButton.setToolTipText("Navigate to the root directory");
		rootButton.setIcon((Icon)new ImageIcon(new ImageIcon("res/round61.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		rootButton.setFocusable(false);
		rootButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Grid.CURRENT_PATH = "/";
				g.updateInterface();
			}
		});
		add(rootButton);
	}
}
