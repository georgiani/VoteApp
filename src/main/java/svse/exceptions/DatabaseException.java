package svse.exceptions;

import logger.ProjectLogger;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseException() {
		super();
	}
	
	public DatabaseException(String m) {
		super(m);
		ProjectLogger.getInstance().log("e", m);
	}
}
