
public class Square_Wall extends Square {

	public Square_Wall(FieldOfView fovGame, char properties) {
		super(fovGame, properties);
		setFriendlyName("Wall");
		setOpen(false);
		setTransparent(false);
	}
}
