/*
 * Toth File Explorer
 * 
 * Created 1/29/2016
 * by Peter Toth
 */

package me.pt.tfe;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.fast.FastLookAndFeel;

import me.pt.tfe.gui.Toolbar;

public class Main {
	
	public static void main(String [] args){
		
		try {
			UIManager.setLookAndFeel(new FastLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Toth File Explorer ALPHA 1.2.0");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		Grid ui = new Grid();
		Toolbar tb = new Toolbar(ui);
		frame.add(tb, BorderLayout.PAGE_START);
		frame.add(ui, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
