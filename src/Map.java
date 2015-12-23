import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


public class Map {

	// Reference to the game object.
	private final FieldOfView game;
	
	// Map metadata.
	private String mapName, mapAuthor, mapVersion, mapRequiredMod;
	
	private char[][] data;
	private Point size;
	
	/**
	 * Initializes and loads the map.
	 * @param fovGame Reference to the game object
	 * @param mapFilePath File path of the map file to load
	 * @throws IOException if the map file itself is invalid.
	 * @throws InvalidMapException if the map couldn't load.
	 */
	public Map(FieldOfView fovGame, String mapFilePath) throws IOException, InvalidMapException {
		game = fovGame;
		loadMapFile(mapFilePath);
	}

	/**
	 * Load the map file into memory.
	 * @param mapFilePath
	 * @throws FileNotFoundException if the map file does not exist.
	 * @throws InvalidMapException if the map file is invalid for any
	 * reason (see message for details).
	 */
	private void loadMapFile(String mapFilePath) throws IOException, InvalidMapException {
		
		// Open the file.
		BufferedReader file = new BufferedReader(new FileReader(mapFilePath));
		
		// Ensure this file is the correct format. This code can only handle format "1",
		// so if the first line of the file is anything else, we throw an error. This way
		// if the format changes in the future, this version won't be unpredictable.
		String mapFileFormat = loadMapFileLine(file, "");
		if (!mapFileFormat.equals("1")) {
			throw new InvalidMapException(InvalidMapException.MESSAGE_FORMAT);
		}
		
		// Load name, author, version, and mod required. Each of these is on its own line.
		mapName = loadMapFileLine(file, "name");
		mapAuthor = loadMapFileLine(file, "author");
		mapVersion = loadMapFileLine(file, "version");
		mapRequiredMod = loadMapFileLine(file, "required mod");
		
		// The next two lines are the size of the map in squares. Unlike the
		// rest of the map, these are stored as text.
		try {
			size = new Point();
			size.x = Integer.parseInt(loadMapFileLine(file, ""));
			size.y = Integer.parseInt(loadMapFileLine(file, ""));
		}
		catch (NumberFormatException e) {
			throw new InvalidMapException(InvalidMapException.MESSAGE_SIZE_FAIL);
		}
		
		// Make sure the map size is reasonable.
		if (size.x < Utils.MIN_MAP_SIZE || size.y < Utils.MIN_MAP_SIZE ||
				size.x > Utils.MAX_MAP_SIZE || size.y > Utils.MAX_MAP_SIZE) {
			throw new InvalidMapException(InvalidMapException.MESSAGE_SIZE_BAD);
		}
	}
	
	/**
	 * Reads in a line from the map file, throwing exceptions if it can't
	 * be read or isn't valid.
	 * @param file BufferedReader for the file
	 * @param fieldName What this line represents (e.g. "name" or "author"), or
	 * an empty string to skip checking for validity
	 * @return The next line in the file.
	 * @throws IOException if something goes wrong when reading it.
	 * @throws InvalidMapException if reached EOF or invalid text.
	 */
	private String loadMapFileLine(BufferedReader file, String fieldName) throws IOException, InvalidMapException {
		String result = file.readLine();
		
		// Check if we reached EOF too early.
		if (result == null) {
			throw new InvalidMapException(InvalidMapException.MESSAGE_EOF);
		}
		
		// If we weren't given a field name, return the result now.
		if (fieldName.isEmpty()) {
			return result;
		}
		
		// Otherwise, make sure it's a valid string and the line starts with fieldName.
		if (!Utils.isValidName(result) || !result.substring(0, fieldName.length()).equals(fieldName)) {
			throw new InvalidMapException("The map's \"" + fieldName + "\" field is invalid.");
		}
		
		return result.substring(fieldName.length() + 1);
	}

}
