package svse.exceptions;

import logger.ProjectLogger;

public class SessioneNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SessioneNotFoundException() {
		super();
	}
	
	public SessioneNotFoundException(String m) {
		super(m);
		ProjectLogger.getInstance().log("i", m);
	}
}
