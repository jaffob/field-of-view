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
	public FieldOfView game;
	private TestController1[] conts;
	private Map map;
	public boolean isEditor;
	private Scanner console;
	String mappath;
	
	public TestMain1() {
		
		console = new Scanner(System.in);
		System.out.print("e for editor, anything else for game: ");
		
		// EDITOR
		if (console.nextLine().equals("e")) {
			System.out.print("Enter map name to load, or blank for new map: ");
			mappath = console.nextLine();
			isEditor = true;
			try {
				map = new Map(mappath);
			} catch (IOException | InvalidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			
			addMouseListener(new MouseAdapter() {
				 public void mousePressed(MouseEvent e) {
					 Vector2D sqpos = new Vector2D(e.getX() / SQSIZE, e.getY() / SQSIZE);
					 if (map.positionIsInBounds(sqpos)) {
						 int sqtype = map.getSquareType(map.getSquare(sqpos).getClass());
						 Square newSquare = map.createSquare((sqtype + 1) % 4, sqpos, 0);
						 map.getSquares()[sqpos.x][sqpos.y] = newSquare;
						 repaint();
					 }
				 }
		    });
		}
		
		// GAME
		else {
			System.out.print("Enter map name to load: ");
			mappath = console.nextLine();
			conts = new TestController1[2];
			conts[0] = new TestController1(this, 1);
			conts[1] = new TestController1(this, 2);
			
			try {
				game = new FieldOfView(mappath, conts);
			} catch (IOException | InvalidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, 1024, 512);
	    
	    if (isEditor) {
		    
		    Square[][] sq = isEditor ? map.getSquares() : game.getMap().getSquares();
		    for (int i = 0; i < sq.length; i++) {
				for (int j = 0; j < sq[0].length; j++) {
					Square s = sq[i][j];
					
					if (s.getClass().equals(Square_Open.class))
						g.setColor(Color.WHITE);
					else if (s.getClass().equals(Square_Wall.class))
						g.setColor(Color.DARK_GRAY);
					else if (s.getClass().equals(Square_Window.class))
						g.setColor(Color.CYAN);
					else if (s.getClass().equals(Square_Start.class))
						g.setColor(Color.YELLOW);
					
					g.fillRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
					g.setColor(Color.BLACK);
					g.drawRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
				}
			}
	    }
	    
	    else {
	    
	    	for (int player = 0; player < 2; player++) {
	    		KnowledgeState ks = conts[player].knowledge;
	    		ClientSquare[][] sq = ks.getCurrentSquares();
	    		for (int i = 0; i < sq.length; i++) {
					for (int j = 0; j < sq[0].length; j++) {
						ClientSquare s = sq[i][j];
						
						if (s.getGameClass().equals(Square_Open.class))
							g.setColor(Color.WHITE);
						else if (s.getGameClass().equals(Square_Wall.class))
							g.setColor(Color.DARK_GRAY);
						else if (s.getGameClass().equals(Square_Window.class))
							g.setColor(Color.CYAN);
						else if (s.getGameClass().equals(Square_Start.class))
							g.setColor(Color.YELLOW);
						
						int sqx = (544 * player) + s.getPosition().x * SQSIZE;
						int sqy = s.getPosition().y * SQSIZE;
						g.fillRect(sqx, sqy, SQSIZE, SQSIZE);
						g.setColor(Color.BLACK);
						g.drawRect(sqx, sqy, SQSIZE, SQSIZE);
						
						if (!s.isKnown()) {
							g.fillRect(sqx + 6, sqy + 6, SQSIZE - 12, SQSIZE - 12);
						}
					}
				}
	    		
	    		for (ClientPiece p : ks.getCurrentPieces()) {
			    	g.setColor(p.getOwner() == 1 ? Color.RED : Color.BLUE);
			    	g.fillOval((544 * player) + p.getPosition().x * SQSIZE + 8, p.getPosition().y * SQSIZE + 8, 16, 16);
			    }
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
		
		window.setSize(1060, 600);
		window.setVisible(true);
		
		if (!game.isEditor)
			System.out.println("player " + game.game.play() + " wins! woot");
	}

	protected void saveMap() {
		if (!isEditor)
			return;
		map.setMapName("Test of Mapping");
		map.setMapAuthor("Jack");
		map.setMapRequiredMod("none");
		map.setMapVersion("1.0");
		
		try {
			map.saveMapFile(mappath.isEmpty() ? "out.fov" : mappath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
