import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;


public class Results extends JPanel {

	public Results (int roll, String line, int daynum, int budget, String name) {
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
		
		JLabel winner = new JLabel("The winner is: " + name);
		winner.setBounds(0,240,400,30);			
		add(winner);
		
		JLabel dats = new JLabel("------------------------------------------------------------------------");
		dats.setBounds(10, 120, 400, 10);
		add(dats);
		
		JLabel information = new JLabel("NOTE: ");
		information.setBounds(0, 150, 400, 30);
		add(information);
		
		JLabel comment1 = new JLabel("Click on the Arrow to move");
		comment1.setBounds(10,180,400,30);	
		add(comment1);
		
		JLabel comment2 = new JLabel("Please end your turn with \"End\" button.");
		comment2.setBounds(10, 210, 400, 30);
		add(comment2);
	}

}
