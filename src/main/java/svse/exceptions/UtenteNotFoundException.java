package svse.exceptions;

import logger.ProjectLogger;

public class UtenteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UtenteNotFoundException() {
		super();
	}
	
	public UtenteNotFoundException(String m) {
		super(m);
		ProjectLogger.getInstance().log("i", m);
	}

}
