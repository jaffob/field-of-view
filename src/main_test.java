import java.io.IOException;


public class main_test {

	public static void main(String[] args) {
		FieldOfView game;
		System.out.println(System.getProperty("user.dir"));
		try {
			game = new FieldOfView("testmap.txt", null, null);
			System.out.println("Player " + game.play() + " has won!");
		} catch (IOException e) {
			System.out.println("Oh no! The map file doesn't exist. " + e.getMessage());
			//e.printStackTrace();
		} catch (InvalidMapException e) {
			System.out.println("Invalid map file: " + e.getMessage());
		}
	}

}
