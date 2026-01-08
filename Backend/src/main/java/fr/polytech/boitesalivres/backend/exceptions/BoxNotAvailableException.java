package fr.polytech.boitesalivres.backend.exceptions;

public class BoxNotAvailableException extends RuntimeException {
    public BoxNotAvailableException(String message) {
        super(message);
    }
}