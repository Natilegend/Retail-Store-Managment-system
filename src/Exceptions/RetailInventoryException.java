package src.Exceptions;

public class RetailInventoryException extends RuntimeException {
    public RetailInventoryException(String message) {
        super(message);
    }

    public RetailInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}