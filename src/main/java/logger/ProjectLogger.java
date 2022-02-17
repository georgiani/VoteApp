package logger;

public class ProjectLogger {
	private static ProjectLogger logger;
	
	public static ProjectLogger getInstance() {
		if (logger == null) {
			return new ProjectLogger();
		}
		return logger;
	}
	
	public void log(String level, String message) {
		if (level.equals("e"))
			System.out.println("ERROR: " + message);
		else if (level.equals("i"))
			System.out.println("INFO: " + message);
	}
}
