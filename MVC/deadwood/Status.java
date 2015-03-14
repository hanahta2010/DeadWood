import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;


public class Status extends JPanel {
		
		public Status(String name, String rank, String location, String dollars, String credits, String role, String rehearseCredit) {
			setLayout(null);
			setBackground(new Color(240,227,213));
			setBounds(0,0,400,300);
		
			JLabel tableLabel = new JLabel("Score Board: \n");
			tableLabel.setBounds(0,30,200,30);				
			add(tableLabel);

			JLabel turn = new JLabel(name + " turn");
			turn.setBounds(10,60,200,30);				
			add(turn);

			JLabel room = new JLabel("Location: " + location);
			room.setBounds(10,90,200,30);			
			add(room);
			
			JLabel job = new JLabel("Role: " + role);
			job.setBounds(10,120,200,30);			
			add(job);
			
			JLabel rehearse = new JLabel("Rehearse credits: " + rehearseCredit);
			rehearse.setBounds(10,150,200,30);			
			add(rehearse);
			
			JLabel level = new JLabel("rank: " + rank);
			level.setBounds(10,180,200,30);				
			add(level);

			JLabel money = new JLabel("dollars: " + dollars);
			money.setBounds(10,210,200,30);			
			add(money);
			
			JLabel credit = new JLabel("credits: " + credits);
			credit.setBounds(10,240,200,30);			
			add(credit);

	
			
			
	}

}
