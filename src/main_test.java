import java.io.IOException;


public class main_test {

	public static void main(String[] args) {
		
		/*Map m;
		try {
			m = new Map(null, null);
			m.saveMapFile("testmap2.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidMapException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		System.exit(0);*/
		
		FieldOfView game;
		try {
			DumbController[] dc = new DumbController[2];
			dc[0] = new DumbController();
			dc[1] = new DumbController();
			game = new FieldOfView("testmap2.txt", dc, null);
			System.out.println("Player " + game.play() + " has won!");
		} catch (IOException e) {
			System.out.println("Oh no! The map file doesn't exist. " + e.getMessage());
			//e.printStackTrace();
		} catch (InvalidMapException e) {
			System.out.println("Invalid map file: " + e.getMessage());
		}
	}

}
