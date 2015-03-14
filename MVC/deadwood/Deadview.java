import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class Deadview {
  
	public JFrame mainFrame;
	private JPanel view;	
	public Boardview board;//extended Jpanel
	public Stats stats; //extended Jpanel  
	private Controller control = new Controller();

	
//constructor--------------------------------------------------------------------------
	public Deadview () {
		
	}
	
	public Deadview (int[] cardorder, int numplayers) throws IOException {

    mainFrame = new JFrame();
    mainFrame.setTitle("Deadwood");
	mainFrame.setSize(1600,925);
	mainFrame.setLayout(null);
	
	board = new Boardview(cardorder, numplayers);
	board.setBounds(0,0,1200,900);	
	mainFrame.add(board);

	stats = new Stats(numplayers);
	mainFrame.add(stats);
	stats.setBounds(1200,0,400,900);
	
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);

	
  }
  
	public Boardview board() {
		return board;
	}

//---------------------------------------------------------------------------------
}

