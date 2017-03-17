package knack.college.learnenglish.exceptions;


public class DatabaseLEException extends Exception {
    public DatabaseLEException(String message) {
        super(message);
    }

    public DatabaseLEException(Exception e) {
        super(e);
    }
}
