package me.pt.tfe.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import me.pt.tfe.Grid;
import me.pt.tfe.file.FileDiscoverer;

public class FilePropertiesUI {

	private String FILE_PATH, FILE_NAME;
	private JFrame frame;
	
	public FilePropertiesUI(String fpath, Grid gridRef){
		
		FILE_PATH = fpath;
		String[] fpathBroken = fpath.split("/");
		FILE_NAME = fpathBroken[fpathBroken.length-1];
		
		frame = new JFrame("Properties - "+FILE_NAME);
		frame.setSize(350, 425);
		frame.setLocationRelativeTo(gridRef);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		JPanel p = new JPanel();
		frame.setContentPane(p);
		p.setLayout(new BorderLayout());
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		p.setBorder(padding);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		
		JPanel northPanelTop = new JPanel();
		northPanelTop.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setFont(Fonts.ARIAL_15);
		northPanelTop.add(nameLabel, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 2;
		
		JLabel fnameLabel = new JLabel(FILE_NAME);
		fnameLabel.setFont(Fonts.ARIAL_18);
		northPanelTop.add(fnameLabel, gc);
		
		gc.weightx = 4;
		
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				new RenameUI(FILE_PATH, FILE_NAME, frame, gridRef, fnameLabel);
			}
		});
		northPanelTop.add(renameButton, gc);
		
		JPanel northPanelMiddle = new JPanel();
		northPanelMiddle.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		
		JLabel typeLabel = new JLabel("Type: ");
		typeLabel.setFont(Fonts.ARIAL_15);
		northPanelMiddle.add(typeLabel, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 2;
		
		JLabel ftypeLabel = new JLabel("File");
		ftypeLabel.setFont(Fonts.ARIAL_18);
		if(FILE_PATH.endsWith("/"))
			ftypeLabel.setText("Directory");
		northPanelMiddle.add(ftypeLabel, gc);
		
		northPanel.add(northPanelTop, BorderLayout.NORTH);
		northPanel.add(northPanelMiddle, BorderLayout.WEST);
		northPanel.add(new JLabel("Preview:"), BorderLayout.SOUTH);
		p.add(northPanel, BorderLayout.NORTH);
		
		JTextArea fPreview = new JTextArea();
		fPreview.setEditable(false);
		if(new File(FILE_PATH).length() < 100000){
			if(!FILE_PATH.endsWith("/")){
				try {
					FileReader fileReader = new FileReader(FILE_PATH);
					@SuppressWarnings("resource")
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String line = null;
					while((line = bufferedReader.readLine()) != null){
						fPreview.append(line+"\n");
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				String[] dirContents = FileDiscoverer.getDirectoryContents(FILE_PATH, false);
				ArrayList<String> dContents = new ArrayList<>();
				for(String s : dirContents)
					dContents.add(s);
				Collections.sort(dContents, String.CASE_INSENSITIVE_ORDER);
				String[] directoryContents = new String[dContents.size()];
				dContents.toArray(directoryContents);
				for(String dir : directoryContents){
					fPreview.append(dir+"\n");
				}
			}
		}else{
			fPreview.setText("File too large to preview!");
		}
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(fPreview);
		fPreview.setCaretPosition(0);
		p.add(jsp, BorderLayout.CENTER);
		
		JButton okButton = new JButton ("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
			}
		});
		p.add(okButton, BorderLayout.SOUTH);
	}
}
