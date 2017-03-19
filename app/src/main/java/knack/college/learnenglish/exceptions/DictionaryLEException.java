package knack.college.learnenglish.exceptions;


public class DictionaryLEException extends Exception {
    public DictionaryLEException(String message) {
        super(message);
    }

    public DictionaryLEException(Exception e) {
        super(e);
    }
}
