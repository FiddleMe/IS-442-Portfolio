package com.BackendServices.PortfolioStock.exception;

public class PortfolioStockException extends RuntimeException {
    public PortfolioStockException(String message) {
        super(message);
    }

    public PortfolioStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
