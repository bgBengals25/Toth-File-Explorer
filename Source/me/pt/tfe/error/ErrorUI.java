package me.pt.tfe.error;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import me.pt.tfe.Grid;
import me.pt.tfe.gui.Fonts;

public class ErrorUI {

	public static void sendException(String message, Grid ref){
		
		JFrame frame = new JFrame("Error!");
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(ref);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		
		JPanel p = new JPanel();
		frame.setContentPane(p);
		p.setLayout(new BorderLayout());
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		p.setBorder(padding);
		
		JLabel errLabel = new JLabel("Error:");
		errLabel.setFont(Fonts.ARIAL_15);
		p.add(errLabel, BorderLayout.NORTH);
		
		JTextArea jta = new JTextArea();
		jta.setText(message);
		jta.setFont(Fonts.UBUNTUMONO_14);
		jta.setCaretPosition(0);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(jta);
		p.add(jsp, BorderLayout.CENTER);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
			}
		});
		p.add(okButton, BorderLayout.SOUTH);
		p.revalidate();
		p.repaint();
	}
}
