package svse.exceptions;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseException() {
		super();
	}
	
	public DatabaseException(String m) {
		super(m);
	}
}
