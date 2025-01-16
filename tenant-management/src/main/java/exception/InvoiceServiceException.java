/**
 * @author 1 Hubertus Seitz
 * @author 2 GitHub Copilot
 */
package exception;

/**
 * Defines a custom exception class named InvoiceServiceException that extends RuntimeException.
 * This custom exception can be used to handle specific error scenarios related to invoice services in your application.
 * The constructor of this class takes a String message as a parameter and passes it to the superclass (RuntimeException) constructor, which allows you to provide a detailed error message when throwing this exception.
 */
public class InvoiceServiceException extends RuntimeException {
    public InvoiceServiceException(String message) {
        super(message);
    }
}
