package Domain.Exceptions;

public class ValidationException extends Exception {
    public ValidationException() {
        super();
    }

    public ValidationException(String msg) {
        super(msg);
    }
}