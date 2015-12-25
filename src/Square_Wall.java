
public class Square_Wall extends Square {

	public Square_Wall(FieldOfView fovGame, char properties) {
		super(fovGame, properties);
		setOpen(false);
		setTransparent(false);
	}

	public static String getFriendlyName() {
		return "Wall";
	}
}
