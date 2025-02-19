package ru.psv.mj.www.pl.jsolve;

public class ResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceException(String fileName, Exception ex) {
        super(String.format("File: %s", fileName), ex);
    }

    public ResourceException(Exception ex) {
        super(ex);
    }
}
