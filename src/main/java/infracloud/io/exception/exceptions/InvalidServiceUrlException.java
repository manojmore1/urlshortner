package infracloud.io.exception.exceptions;

public class InvalidServiceUrlException extends RuntimeException{
	private static final long serialVersionUID = -5521919052780579756L;

	public InvalidServiceUrlException(String message) {
		super(message);
	}
}
