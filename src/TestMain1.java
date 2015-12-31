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
import javax.swing.SwingUtilities;


public class TestMain1 extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final JPanel buttons = new JPanel();
	private final int SQSIZE = 32;
	public FieldOfView game;
	private TestController2[] conts;
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
					 if (!map.positionIsInBounds(sqpos)) {
						 return;
					 }
					 if (SwingUtilities.isLeftMouseButton(e)) {
						 int sqtype = map.getSquareType(map.getSquare(sqpos).getClass());
						 Square newSquare = map.createSquare((sqtype + 1) % 5, sqpos, 0);
						 map.getSquares()[sqpos.x][sqpos.y] = newSquare;
					 }
					 else {
						 map.getSquares()[sqpos.x][sqpos.y].setSide( (map.getSquares()[sqpos.x][sqpos.y].getSide() + 1) % 3);
					 }
					 
					 repaint();
				 }
		    });
			
			JButton save = new JButton("Save");
			buttons.add(save);
			save.addActionListener(new ActionListener() {

		        @Override
		        public void actionPerformed(ActionEvent e) {
		            saveMap();
		        }
		    });
		}
		
		// GAME
		else {
			System.out.print("Enter map name to load: ");
			mappath = console.nextLine();
			conts = new TestController2[2];
			conts[0] = new TestController2(this, 1);
			conts[1] = new TestController2(this, 2);
			
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
					
					g.setColor(getSquareColor(s.getClass()));
					
					g.fillRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
					g.setColor(Color.BLACK);
					g.drawRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
					
					switch (s.getSide()) {
					case 1:
						g.setColor(Color.RED);
						break;
					case 2:
						g.setColor(Color.BLUE);
						break;
					default:
						g.setColor(Color.WHITE);
					}
					
					g.fillRect(s.getPosition().x * SQSIZE + 1, s.getPosition().y * SQSIZE + 1, 8, 8);
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
						
						g.setColor(getSquareColor(s.getGameClass()));
						
						int sqx = (544 * player) + s.getPosition().x * SQSIZE;
						int sqy = s.getPosition().y * SQSIZE;
						g.fillRect(sqx, sqy, SQSIZE, SQSIZE);
						g.setColor(Color.BLACK);
						g.drawRect(sqx, sqy, SQSIZE, SQSIZE);
						
						if (!s.isKnown()) {
							g.fillRect(sqx + 3, sqy + 3, SQSIZE - 6, SQSIZE - 6);
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
		
		
		window.getContentPane().add(buttons, BorderLayout.SOUTH);
		
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
	
	public Color getSquareColor(Class<? extends Square> cl) {
		if (cl.equals(Square_Open.class))
			return Color.WHITE;
		else if (cl.equals(Square_Window.class))
			return Color.CYAN;
		else if (cl.equals(Square_Start.class))
			return Color.YELLOW;
		else if (cl.equals(Square_Victory.class))
			return Color.GREEN;
		return Color.DARK_GRAY;
	}

	/*@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("AISDHOSD");
		Direction d;
		switch (e.getKeyChar()) {
		case 'a':
			d = Direction.LEFT;
			break;
		case 'w':
			d = Direction.UP;
			break;
		case 'd':
			d = Direction.RIGHT;
			break;
		case 's':
			d = Direction.DOWN;
			break;
		default:
			return;
		}
		conts[0].inputMove = d;
		conts[1].inputMove = d;
	}*/
}
