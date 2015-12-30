import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class TestMain1 extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map map;

	public TestMain1() {
		/*FieldOfView game;
		try {
			TestController1[] dc = new TestController1[2];
			dc[0] = new TestController1();
			dc[1] = new TestController1();
			game = new FieldOfView("testmap2.txt", dc, null);
			System.out.println("Player " + game.play() + " has won!");
		} catch (IOException e) {
			System.out.println("Oh no! The map file doesn't exist. " + e.getMessage());
			//e.printStackTrace();
		} catch (InvalidMapException e) {
			System.out.println("Invalid map file: " + e.getMessage());
		}*/
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.RED);
	    g.drawRect(50, 50, 50, 100);
	}

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setSize(512, 512);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		TestMain1 game = new TestMain1();
		window.add(game, BorderLayout.CENTER);
	}
}
