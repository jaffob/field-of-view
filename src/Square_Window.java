
public class Square_Window extends Square {

	public Square_Window(FieldOfView fovGame, Vector2D position, char properties) {
		super(fovGame, position, properties);
		setOpen(false);
	}

	@Override
	public String getFriendlyName() {
		return "Window";
	}

}
