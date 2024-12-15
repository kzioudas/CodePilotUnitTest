package codepilotunittest.representations;

public class MethodNotFoundException extends Throwable {

	private static final long serialVersionUID = 7821897121382988079L;

	public MethodNotFoundException(String message) {
		super(message);
	}
}