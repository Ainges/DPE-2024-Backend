package exception;

public class InvoiceServiceException extends RuntimeException {
    public InvoiceServiceException(String message) {
        super(message);
    }
}
