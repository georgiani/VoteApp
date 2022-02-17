package svse.exceptions;

import logger.ProjectLogger;

public class ListaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ListaNotFoundException() {
		super();
	}
	
	public ListaNotFoundException(String m) {
		super(m);
		ProjectLogger.getInstance().log("i", m);
	}

}
