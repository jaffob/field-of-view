import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;


public final class Map {

	protected final FieldOfView game;
	public static final String MAP_FILE_FORMAT = "1";
	
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
	
	// ---------------------------------- //
	// ------------- Loading ------------ //
	// ---------------------------------- //

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
		if (!mapFileFormat.equals(MAP_FILE_FORMAT)) {
			throw new InvalidMapException(InvalidMapException.MESSAGE_FORMAT);
		}
		
		// Load name, author, version, and mod required. Each of these is on its own line.
		setMapName(loadMapFileLine(file, "name"));
		setMapAuthor(loadMapFileLine(file, "author"));
		setMapVersion(loadMapFileLine(file, "version"));
		setMapRequiredMod(loadMapFileLine(file, "required mod"));
		
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
			Vector2D pos = new Vector2D();
			pos.x = (i/2) % getSize().x;
			pos.y = (i/2) / getSize().x;
			
			squareObjects[pos.x][pos.y] = game.createSquare(squareData[i], pos, squareData[i + 1]);
			if (squareObjects[pos.x][pos.y] == null) {
				throw new InvalidMapException("The square at " + pos + " is invalid.");
			}
		}
		
		return squareObjects;
	}
	
	
	// ---------------------------------- //
	// ------------- Saving ------------- //
	// ---------------------------------- //
	
	/**
	 * Save the map as it is currently to a file.
	 * @param mapFilePath
	 * @throws FileNotFoundException if the map file does not exist.
	 * @throws InvalidMapException if the map file is invalid for any
	 * reason (see message for details).
	 */
	public void saveMapFile(String mapFilePath) throws IOException {
		// Open the file for writing.
		BufferedWriter file = new BufferedWriter(new FileWriter(mapFilePath));
		
		// Write the format version to the first line. We always save in the most current
		// format, so we use MAP_FILE_FORMAT regardless of what format the map loaded in.
		saveMapFileLine(file, "", MAP_FILE_FORMAT);
		
		// Save name, author, version, and mod required. Each of these is on its own line.
		saveMapFileLine(file, "name", getMapName());
		saveMapFileLine(file, "author", getMapAuthor());
		saveMapFileLine(file, "version", getMapVersion());
		saveMapFileLine(file, "required mod", getMapRequiredMod());
		
		// The next two lines are the size of the map in squares. Unlike the
		// rest of the map, these are stored as text.
		saveMapFileLine(file, "", "" + getSize().x);
		saveMapFileLine(file, "", "" + getSize().y);
		
		// The rest of the file stores all of the squares. Dump the squares array.
		saveMapFileSquares(file);
	}

	private void saveMapFileLine(BufferedWriter file, String fieldName, String line) throws IOException {
		file.write((fieldName.isEmpty() ? "" : fieldName + " ") + line);
	}
	
	private void saveMapFileSquares(BufferedWriter file) throws IOException {
		char[] squareData = new char[getNumSquares() * 2];
		
		// For every Square, put 2 chars into the array.
		for (int i = 0; i < squareData.length; i += 2) {
			Square sq = getSquare((i/2) % getSize().x, (i/2) / getSize().x);
			squareData[i] = (char)game.getSquareType(sq.getClass());
			squareData[i+1] = (char)sq.createPropertyVal();
		}
		
		file.write(squareData);
	}
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public String getMapName() {
		return mapName;
	}

	private void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapAuthor() {
		return mapAuthor;
	}

	private void setMapAuthor(String mapAuthor) {
		this.mapAuthor = mapAuthor;
	}

	public String getMapVersion() {
		return mapVersion;
	}

	private void setMapVersion(String mapVersion) {
		this.mapVersion = mapVersion;
	}

	public String getMapRequiredMod() {
		return mapRequiredMod;
	}

	private void setMapRequiredMod(String mapRequiredMod) {
		this.mapRequiredMod = mapRequiredMod;
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
	
	public Square getSquare(int x, int y) {
		if (!positionIsInBounds(x, y))
			return null;
		return getSquares()[x][y];
	}
	
	public Vector2D getSize() {
		return size;
	}

	public int getNumSquares() {
		return getSize().x * getSize().y;
	}
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //
	
	/**
	 * Check if a position is within the bounds of the map.
	 * @param pos Vector2D representing the position
	 * @return Whether it's in bounds
	 */
	public boolean positionIsInBounds(Vector2D pos) {
		return pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y;
	}
	
	public boolean positionIsInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < size.x && y < size.y;
	}
	
	public ArrayList<Square> getSquaresOfType(Class<? extends Square> type) {
		ArrayList<Square> s = new ArrayList<Square>();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				if (squares[i][j].getClass().equals(type)) {
					s.add(squares[i][j]);
				}
			}
		}
		return s;
	}

	public ArrayList<Vector2D> getVisibleSquares(Piece piece) {
		// A very dumb version of this method that calls all squares along
		// the piece's axes visible, and none others.
		ArrayList<Vector2D> out = new ArrayList<Vector2D>();
		for (int i = 0; i < getSize().x; i++) {
			out.add(new Vector2D(i, piece.getPosition().y));
		}
		for (int i = 0; i < getSize().y; i++) {
			if (i != piece.getPosition().y)
				out.add(new Vector2D(piece.getPosition().x, i));
		}
		return out;
	}
	
	@Override
	public String toString() {
		return getMapName() + " (Version " + getMapVersion() + ") by " + getMapAuthor() + " [" + getSize().x + "x" + getSize().y + "]";
	}
}
