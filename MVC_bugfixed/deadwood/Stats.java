import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;


/*Hang:  
*/
public class Stats extends JPanel{

	private JLabel frame;	
	public Status sub1;
	private JPanel sub2;
	public JPanel sub3;
	
	private JTable table;
	
	private JButton end;
	private JButton act;
	private JButton rehearse;
	
	private JLabel day;
	private JLabel Line;
	private JTextArea text;
	public Stats(int numPlayer) {
		super (null);
		setBounds(1200, 0, 400, 900);
		setLayout(null);		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(400, 900);
		setDoubleBuffered(true);
//------------------------------------------------------------------------		

		frame = new JLabel();
		frame.setSize(new Dimension(400, 300));
		frame.setBounds(0,0,400,300);
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("../images/reel.png", "i"))));
			frame.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
//--------------------------------------------------------------------------

		  String rank;
		  if (numPlayer <= 6) {
			  rank = "1";
		  } else {
			  rank = "2";
		  }
		  
		sub1 = new Status("b", rank, "trailer", "0", "0", "No role", "0");
		/*sub1 = new JPanel();
		sub1.setBackground(new Color(240,227,213));
		//sub1.setLayout(new GridLayout(numPlayer, 5));
		sub1.setLayout(null);
		


		//sub1.add(frame);
		JLabel tableLabel = new JLabel("Score Board: \n");
		tableLabel.setBounds(0,0, 300,40);		
		table = new JTable();
		sub1.add(tableLabel);*/
//-------------------------------------------------------------------------
		
		sub2 = new JPanel();
		sub2.setBounds(0,300,400,300);		
		sub2.setLayout(null);		
		//sub2.setBackground(new Color(173,126,75));
		sub2.setBackground(Color.WHITE);
		act = new JButton();
		act.setPreferredSize(new Dimension(70, 70));
		act.setBounds(85,40,70,70);
		act.setBorder(BorderFactory.createEmptyBorder());
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("../images/act.jpg", "i"))));
			act.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
			
		act.setBackground(new Color(255,0,0,0));		
		act.addActionListener(
	  			new ActionListener() {
	  				@Override
					public void actionPerformed(ActionEvent event) {
						Controller.act();
						Controller.refreshStats();          				
					}
	  	});
		
		rehearse = new JButton();
		rehearse.setPreferredSize(new Dimension(70,70));
		rehearse.setBounds(160,120,70,70);	
		rehearse.setBorder(BorderFactory.createEmptyBorder());
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("../images/rehearse.jpg", "i"))));
			rehearse.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
		rehearse.addActionListener(
	  			new ActionListener() {
	  				@Override
					public void actionPerformed(ActionEvent event) {
						Controller.rehearse(Boardview.currentPlayer.name);          				
						Controller.refreshStats();
						Controller.refreshResults("You tried to rehearse. Are you in a role?");
					}
	  	});
		
		end = new JButton();
		end.setPreferredSize(new Dimension(70, 70));
		end.setBounds(240,190,70,70);	
		end.setBorder(BorderFactory.createEmptyBorder());
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("../images/end2.jpg", "i"))));
			end.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
		end.addActionListener(
	  			new ActionListener() {
	  				@Override
					public void actionPerformed(ActionEvent event) {
						Controller.end();
						//Controller.refreshStats();          				
					}
	  	});
		sub2.add(frame);
		
		sub2.add(act);		
		sub2.add(rehearse);
		sub2.add(end);
//-------------------------------------------------------------------------

		sub3 = new Results(0, "Welcome to Deadwood. Ready to act?", 1, 0, "Not yet");
		sub3.setBounds(0,600,400,300);		
		sub3.setBackground(new Color(213,226,240));
		
		/*JLabel dats = new JLabel("------------------------------------------------------------------------");
		dats.setBounds(10, 120, 400, 10);
		JLabel information = new JLabel("NOTE: ");
		information.setBounds(0, 150, 400, 30);
		JLabel comment1 = new JLabel("Click on the Arrow to move");
		comment1.setBounds(10,180,400,30);	
		JLabel comment2 = new JLabel("Please end your turn with \"End\" button.");
		comment2.setBounds(10, 210, 400, 30);
		
		sub3.add(dats);
		sub3.add(information);
		sub3.add(comment1);
		sub3.add(comment2); */
		
//-----------------------------------------------------------------------		
		
		this.add(sub1);
		this.add(sub2);
		this.add(sub3);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*private JButton dummy;
private JButton player1;
private JButton text;

public Stats() {
	
	super(null);
	setLayout(null);
    setSize(400,900);
    setDoubleBuffered(true);
    
	player1 = new JButton();
	add(player1);	
	player1.setBounds(200,200,46,46);

	text = new JButton();  	
	text.setText("<html><h1><font color=red><b>STUFF GOES HERE</b></html></h1>");
    add(text);
    text.setBounds(0,0,400,900);


}*/

//--------------------------------------------------------------------------------------------------------------
}
