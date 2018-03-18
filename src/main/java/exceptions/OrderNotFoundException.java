package exceptions;

public class OrderNotFoundException extends AppException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
