
public class Square_Wall extends Square {

	public Square_Wall(char properties) {
		super(properties);
		setOpen(false);
		setTransparent(false);
	}

	public static String getFriendlyName() {
		return "Wall";
	}
}
