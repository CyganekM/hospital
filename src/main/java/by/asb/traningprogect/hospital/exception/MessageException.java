package by.asb.traningprogect.hospital.exception;

public enum MessageException {

    NOT_FOUND_ENTITY("The entity with id = %s not found"),
    ENTITY_EXISTS("The entity with ID = %s already exists in the database");

    private final String message;

    private MessageException(String message) {
        this.message = message;
    }

    public String getName() {
        return message;
    }
}
