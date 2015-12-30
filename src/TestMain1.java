import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TestMain1 extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int SQSIZE = 32;
	private FieldOfView game;
	private Controller[] conts;
	private Map map;
	private boolean isEditor;

	public TestMain1() {
		
		Scanner console = new Scanner(System.in);
		System.out.print("e for editor, anything else for game: ");
		
		// EDITOR
		if (console.nextLine().equals("e")) {
			isEditor = true;
			try {
				map = new Map("");
			} catch (IOException | InvalidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			addMouseListener(new MouseAdapter() {
				 public void mousePressed(MouseEvent e) {
					 Vector2D sqpos = new Vector2D(e.getX() / SQSIZE, e.getY() / SQSIZE);
					 if (map.positionIsInBounds(sqpos)) {
						 int sqtype = map.getSquareType(map.getSquare(sqpos).getClass());
						 Square newSquare = map.createSquare((sqtype + 1) % 3, sqpos, 0);
						 map.getSquares()[sqpos.x][sqpos.y] = newSquare;
						 repaint();
					 }
				 }
		    });
		}
		
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
	    g.fillRect(0, 0, 512, 512);
	    Square[][] sq = map.getSquares();
	    for (int i = 0; i < sq.length; i++) {
			for (int j = 0; j < sq[0].length; j++) {
				Square s = sq[i][j];
				
				if (s.getClass().equals(Square_Open.class))
					g.setColor(Color.WHITE);
				else if (s.getClass().equals(Square_Wall.class))
					g.setColor(Color.DARK_GRAY);
				else if (s.getClass().equals(Square_Window.class))
					g.setColor(Color.CYAN);
				
				g.fillRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
				g.setColor(Color.BLACK);
				g.drawRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
			}
		}
	}

	public static void main(String[] args) {
		JFrame window = new JFrame();

		TestMain1 game = new TestMain1();
		window.getContentPane().add(game, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel buttonsPanel = new JPanel();
		JButton save = new JButton("Save");
		buttonsPanel.add(save);
		window.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		save.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            game.saveMap();
	        }
	    });
		
		window.setSize(800, 800);
		window.setVisible(true);
	}

	protected void saveMap() {
		try {
			map.saveMapFile("output_map.fov");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
