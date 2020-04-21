package exceptions;

public class TodoException extends RuntimeException {

    public TodoException(String message, Throwable e) {
        super(message, e);
    }

}
