package dev.ifeoluwa.payaza.application.exception;

/**
 * @author on 26/03/2023
 * @project
 */
public class TransactionNotVerifiedException extends RuntimeException{

    public TransactionNotVerifiedException(String message) {
        super(message);
    }
}
