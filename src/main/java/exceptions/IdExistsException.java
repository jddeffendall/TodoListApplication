package exceptions;

public class IdExistsException extends RuntimeException {

    public IdExistsException(String id) {
        super("Id " + id + " already exists in local database!");
    }

}
