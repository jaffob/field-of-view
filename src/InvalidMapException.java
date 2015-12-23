
public class InvalidMapException extends Exception {
	
	private static final long serialVersionUID = 7649370381278810937L;

	public static final String MESSAGE_EOF = "Unexpectedly reached the end of the map file.";
	public static final String MESSAGE_FORMAT = "The map file is of an unsupported format.";
	public static final String MESSAGE_SIZE_FAIL = "The map's dimensions could not be read.";
	public static final String MESSAGE_SIZE_BAD = "The map's recorded dimensions are not valid.";
	
	public InvalidMapException() {
		super();
	}

	public InvalidMapException(String message) {
		super(message);
	}

	public InvalidMapException(Throwable arg0) {
		super(arg0);
	}

	public InvalidMapException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidMapException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
