package com.BackendServices.PortfolioSector.exception;

public class PortfolioSectorException extends RuntimeException {
    public PortfolioSectorException(String message) {
        super(message);
    }

    public PortfolioSectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
