package me.pt.tfe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import me.pt.tfe.file.FileDiscoverer;
import me.pt.tfe.gui.Fonts;

public class Grid extends JPanel{

	private static final long serialVersionUID = 1L;

	public String CURRENT_PATH = "/home/"+System.getProperty("user.name")+"/";
	
	private JTextField pathField;
	DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> contents = new JList<>(listModel);
	
	public Grid(){
		
		setLayout(new BorderLayout());
		setBackground(new Color(220, 220, 220));
		
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel pLabel = new JLabel("Path -> ");
		northPanel.add(pLabel, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 2;
		pathField = new JTextField(CURRENT_PATH);
		pathField.setFont(Fonts.ARIAL_18);
		pathField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(new File(pathField.getText()).exists()){
					CURRENT_PATH = pathField.getText();
					System.out.println(CURRENT_PATH);
					updateInterface();
				}else{
					pathField.setText(CURRENT_PATH);
				}
			}
			
		});
		northPanel.add(pathField, gbc);
		add(northPanel, BorderLayout.PAGE_START);
		
		
		
		updateInterface();
	}
	
	@SuppressWarnings("unchecked")
	public void updateInterface(){
		
		listModel.clear();
		String[] dirContents = FileDiscoverer.getDirectoryContents(CURRENT_PATH, false);
		for (String file : dirContents){
			listModel.addElement(file);
		}
		@SuppressWarnings("rawtypes")
		Collection list = Collections.list(listModel.elements());
		Collections.sort((List<String>) list, String.CASE_INSENSITIVE_ORDER);
		listModel.clear();
		for(Object o:list){ if(o.toString().endsWith("/")){listModel.addElement("D -> "+(String)o);}else{listModel.addElement("F -> "+(String)o);} }
		contents = new JList<>(listModel);
		contents.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				@SuppressWarnings("rawtypes")
				JList list = (JList)evt.getSource();
				if(evt.getClickCount() == 2 || evt.getClickCount() == 3){
					int index = list.locationToIndex(evt.getPoint());
					String[] chop = listModel.getElementAt(index).split(" ");
					String dir = chop[chop.length-1];
					if(dir.endsWith("/")){
						CURRENT_PATH += dir;
						updateInterface();
					}
				}
			}
		});
		
		add(new JScrollPane(contents), BorderLayout.CENTER);
		
		pathField.setText(CURRENT_PATH);
	}
}
