/*
 * RenameUI.java
 * 
 * Created by Peter Toth
 * on 1/31/2016
 */

package me.pt.tfe.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import me.pt.tfe.Grid;
import me.pt.tfe.file.FileActions;
import me.pt.tfe.file.FileDiscoverer;

public class RenameUI {

	public RenameUI(String oldPath, String oldName, Container parent, Grid gridRef, JLabel nameRef){
		
		JFrame frame = new JFrame("Rename ? "+oldName);
		frame.setSize(350, 125);
		frame.setLocationRelativeTo(parent);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		JPanel p = new JPanel();
		frame.setContentPane(p);
		p.setLayout(new BorderLayout());
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		p.setBorder(padding);
		
		JLabel topLabel = new JLabel("Enter new name:");
		topLabel.setFont(Fonts.ARIAL_18);
		p.add(topLabel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		JLabel oldnameLabel = new JLabel(oldName+" -> ");
		oldnameLabel.setFont(Fonts.ARIAL_16);
		centerPanel.add(oldnameLabel, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 2;
		
		JTextField jtf = new JTextField();
		jtf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					if(!jtf.getText().equals("")){
						FileActions.renameFile(oldPath, FileDiscoverer.getDirectoryOf(oldPath)+jtf.getText());
						nameRef.setText(jtf.getText());
						gridRef.updateInterface();
						frame.dispose();
					}
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
		});
		centerPanel.add(jtf, gc);
		
		p.add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
			}
		});
		
		southPanel.add(cancelButton);
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					if(!jtf.getText().equals("")){
						FileActions.renameFile(oldPath, FileDiscoverer.getDirectoryOf(oldPath)+jtf.getText());
						nameRef.setText(FileDiscoverer.getDirectoryOf(oldPath)+jtf.getText());
						gridRef.updateInterface();
						frame.dispose();
					}
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
		});
		
		southPanel.add(okButton);
		
		p.add(southPanel, BorderLayout.SOUTH);
	}
}
