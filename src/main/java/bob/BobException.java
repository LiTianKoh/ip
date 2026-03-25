package bob;

/**
 * Custom exception for user input errors in Bob.
 * This helps distinguish between system errors and user input errors.
 */
public class BobException extends Exception {
    
    public BobException(String message) {
        super(message);
    }
    
    // For wrapping other exceptions
    public BobException(String message, Throwable cause) {
        super(message, cause);
    }
}
