package de.thi;

/**
 * The class NotificationRouteException extends the RuntimeException class.
 * It defines a constructor that takes a string (String) as a message and passes this message to the constructor of the superclass (RuntimeException).
 * This allows the creation of custom exceptions with a specific error message.
 */
public class NotificationRouteException extends RuntimeException {
    public NotificationRouteException(String message) {
        super(message);
    }
}
