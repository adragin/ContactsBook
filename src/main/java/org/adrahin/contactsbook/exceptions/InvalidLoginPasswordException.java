package org.adrahin.contactsbook.exceptions;

public class InvalidLoginPasswordException extends RuntimeException {
    public InvalidLoginPasswordException(String msg) {
        super(msg);
    }
}
