package svse.exceptions;

import logger.ProjectLogger;

public class CandidatoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CandidatoNotFoundException() {
		super();
	}
	
	public CandidatoNotFoundException(String m) {
		super(m);
		ProjectLogger.getInstance().log("i", m);
	}

}
