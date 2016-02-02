/*
 * Grid.java
 * 
 * Created by Peter Toth
 * on 1/29/2016
 */

package me.pt.tfe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import me.pt.tfe.error.ErrorUI;
import me.pt.tfe.file.FileDiscoverer;
import me.pt.tfe.file.res.properties.PropertyParser;
import me.pt.tfe.gui.FilePropertiesUI;
import me.pt.tfe.gui.Fonts;

public class Grid extends JPanel{

	private static final long serialVersionUID = 1L;

	public static String CURRENT_PATH = "/home/"+System.getProperty("user.name")+"/";
	
	private JTextField pathField;
	DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> contents = new JList<>(listModel);
	
	public JPanel jPanelRef;
	
	public Grid(){
		
		jPanelRef = this;
		
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
					if(!CURRENT_PATH.endsWith("/"))
						CURRENT_PATH += "/";
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
		String[] dirContents = FileDiscoverer.getDirectoryContents(CURRENT_PATH, CURRENT_PATH, false, (Grid)jPanelRef);
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
				if(evt.getButton() == MouseEvent.BUTTON1){
					if(evt.getClickCount() == 2 || evt.getClickCount() == 3){
						int index = list.locationToIndex(evt.getPoint());
						String[] chop = listModel.getElementAt(index).split(" ");
						String dir = chop[chop.length-1];
						if(dir.endsWith("/")){
							CURRENT_PATH += dir;
							updateInterface();
						}else{
							try {
								Desktop.getDesktop().open(new File(CURRENT_PATH+dir));
							} catch (IOException e) {
								ErrorUI.sendException("Grid.java -> Failed to open '"+dir+"'!\n\n"+e.getStackTrace(), (Grid)jPanelRef);
							}
						}
					}
				}else if(evt.getButton() == MouseEvent.BUTTON3){
					if(evt.getClickCount() == 1){
						int index = list.locationToIndex(evt.getPoint());
						String[] chop = listModel.getElementAt(index).split(" ");
						String file = chop[chop.length-1];
						String pfile = CURRENT_PATH + file;
						contents.setSelectedIndex(index);
						
						
						JPopupMenu fpop = new JPopupMenu();
						
						JMenuItem openItem = new JMenuItem("Open");
						openItem.setFont(Fonts.DROIDSANS_12_BOLD);
						openItem.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									if (!pfile.endsWith("/")){
										Desktop.getDesktop().open(new File(pfile));
									}else{
										CURRENT_PATH = pfile;
										updateInterface();
									}
								} catch (IOException ioe) {
									ErrorUI.sendException("Grid.java -> Failed to open '"+pfile+"'!\n\n"+ioe.getStackTrace(), (Grid)jPanelRef);
									ioe.printStackTrace();
								}
							}
						});
						fpop.add(openItem);
						
						if(pfile.endsWith(".sh") || pfile.endsWith(".bash")){
							
							PropertyParser configParser = new PropertyParser("config.properties");
							String[] val = {"defaultTerminal"};
							val = configParser.getPropValues(val, (Grid)jPanelRef);
							String defaultTerminal = val[0];
							
							JMenuItem runInTerminalItem = new JMenuItem("Run In Terminal");
							runInTerminalItem.setFont(Fonts.DROIDSANS_12_BOLD);
							runInTerminalItem.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										String[] termCommand = defaultTerminal.split(" ");
										String[] fullCommand = new String[termCommand.length+1];
										for (int j = 0; j < termCommand.length; j++){
											fullCommand[j] = termCommand[j];
										}
										fullCommand[fullCommand.length-1] = pfile;
										Runtime.getRuntime().exec(fullCommand);
									} catch (IOException e1) {
										ErrorUI.sendException("Grid.java -> Failed to run file in terminal using command: "+defaultTerminal+" "+pfile+"\n\n"+e1.getStackTrace(), (Grid)jPanelRef);
									}
								}
							});
							fpop.add(runInTerminalItem);
						}
						
						fpop.addSeparator();
						
						JMenuItem propertiesItem = new JMenuItem("Properties");
						propertiesItem.setFont(Fonts.ARIAL_12);
						propertiesItem.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								new FilePropertiesUI(pfile, CURRENT_PATH, (Grid)jPanelRef);
							}
						});
						fpop.add(propertiesItem);
						fpop.show(evt.getComponent(), evt.getX(), evt.getY());
					}
				}
			}
		});
		contents.setFont(Fonts.UBUNTUMONO_16);
		add(new JScrollPane(contents), BorderLayout.CENTER);
		
		pathField.setText(CURRENT_PATH);
	}
}
