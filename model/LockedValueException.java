package model;

/**
 * An exception that is thrown when a square denies access to
 * its value.
 * @author Jessie Perce
 */
public class LockedValueException extends Exception {
    /**
     * Constructs a new LockedValueException with a user message.
     * @param message entered by user, will be displayed upon throwing.
     */
    public LockedValueException(String message) {
        super(message);
    }
}
