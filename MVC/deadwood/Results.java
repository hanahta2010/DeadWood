import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;


public class Results extends JPanel {

		public Results (int roll, String line, int daynum, int budget) {
			setLayout(null);
			setBackground(new Color(240,227,213));
			setBounds(0,600,400,300);
		
			JLabel tableLabel = new JLabel("Action: \n");
			tableLabel.setBounds(0,30,200,30);				
			add(tableLabel);

			JLabel day = new JLabel("Day: " + Integer.toString(daynum));
			day.setBounds(10,60,200,30);				
			add(day);

			JLabel comment = new JLabel(line);
			comment.setBounds(10,90,400,30);			
			add(comment);
	}

}
