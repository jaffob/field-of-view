import java.io.FileNotFoundException;
import java.io.FileReader;


public class Map {

	private final FieldOfView game;
	private char[][] data;
	
	public Map(FieldOfView fovGame, String mapFilePath) throws FileNotFoundException, InvalidMapException {
		game = fovGame;
		loadMapFile(mapFilePath);
	}

	/**
	 * 
	 * @param mapFilePath
	 * @throws FileNotFoundException if the map file does not exist.
	 * @throws InvalidMapException if the map file is invalid for any
	 * reason (see message for details).
	 */
	private void loadMapFile(String mapFilePath) throws FileNotFoundException, InvalidMapException {
		FileReader file = new FileReader(mapFilePath);
		throw new InvalidMapException("testing exception");
	}

}
