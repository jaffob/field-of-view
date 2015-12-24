import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;


public class Map {

	// Reference to the game object.
	private final FieldOfView game;
	
	// Map metadata.
	private String mapName, mapAuthor, mapVersion, mapRequiredMod;
	
	private Square[][] squares;
	private Vector2D size;
	
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
	public void loadMapFile(String mapFilePath) throws IOException, InvalidMapException {
		
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
			size = new Vector2D();
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
		
		// The rest of the file stores all of the squares. Load that into the squares array.
		squares = loadMapFileSquares(file);
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
	
	private Square[][] loadMapFileSquares(BufferedReader file) throws IOException, InvalidMapException {
		
		// Create an empty array of squares, and an array of chars. Each square is
		// represented by 2 chars, the first identifying the type of square, and the
		// second specifying properties that apply to that square (siding, rotation, etc).
		Square[][] squareObjects = new Square[getSize().x][getSize().y];
		char[] squareData = new char[getNumSquares() * 2];
		
		// Read all of the square data into the char array, throwing an exception if it ends early.
		if (file.read(squareData, 0, squareData.length) < squareData.length) {
			throw new InvalidMapException("The map file does not contain enough squares.");
		}
		
		// For each pair of chars, create a square and put it in the final array.
		for (int i = 0; i < squareData.length; i += 2) {
			
		}
		
		return squareObjects;
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	
	/**
	 * Get the square at a certain position (origin is top left).
	 * @param pos Vector2D representing the position
	 * @return Square at that position.
	 */
	public Square getSquare(Vector2D pos) {
		if (!positionIsInBounds(pos))
			return null;
		return getSquares()[pos.x][pos.y];
	}
	
	public Vector2D getSize() {
		return size;
	}

	public int getNumSquares() {
		return getSize().x * getSize().y;
	}
	
	/**
	 * Check if a position is within the bounds of the map.
	 * @param pos Vector2D representing the position
	 * @return Whether it's in bounds
	 */
	public boolean positionIsInBounds(Vector2D pos) {
		return pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y;
	}

	@Override
	public String toString() {
		return mapName + " (Version " + mapVersion + ") by " + mapAuthor + " [" + size.x + "x" + size.y + "]";
	}

}
