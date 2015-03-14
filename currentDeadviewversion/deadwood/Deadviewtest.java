import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class Deadviewtest {
  
	private JFrame mainFrame;
	private JPanel view;	
	private JLabel board;
	private JPanel stats;  
	private Controller control = new Controller();

//constructor--------------------------------------------------------------------------
  private Deadviewtest () throws IOException {

    mainFrame = new JFrame();
    mainFrame.setTitle("Deadwood");
	mainFrame.setSize(1600,925);
	mainFrame.setLayout(null);
	
	board = new JLabel();
		mainFrame.add(board);
		board.setBounds(0,0,1200,900);
		try {
			ImageIcon image = new ImageIcon (
		      ImageIO.read(
		        new File(String.format("images/board.jpg", "i"))));//board.jpg has to be in this folder relative to current folder
			board.setIcon(image);
		}catch (IOException e) {
		  e.printStackTrace();
		  System.exit(1);
		}
	stats = new JPanel();
		stats.setBounds(1200,0,400,900);		
		stats.setLayout(null);
		stats.setSize(400,900);
		stats.setBounds(1200,0,400,900);		
		stats.setDoubleBuffered(true);
		
		JLabel text = new JLabel();  	
		text.setText("<html><h1><font color=red><b>STUFF GOES HERE</b></html></h1>");
		stats.add(text);
		text.setBounds(0,0,400,900);

	mainFrame.add(stats);

	
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);
  }
  
//test function-----------------------------------------------------------------------------------  
  public static void main(String[] args) {
    try {
      Deadviewtest dv = new Deadviewtest();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//---------------------------------------------------------------------------------
}

