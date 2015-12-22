
public class main_test {

	public static void main(String[] args) {
		FieldOfView game = new FieldOfView("testmap.txt");
		System.out.println("Player " + game.play() + " has won!");
	}

}
