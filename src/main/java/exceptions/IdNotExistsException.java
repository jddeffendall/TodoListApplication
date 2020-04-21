package exceptions;

public class IdNotExistsException extends RuntimeException {

    public IdNotExistsException(String id) {
        super("This ID doesn't exist in the database!");
    }

}
