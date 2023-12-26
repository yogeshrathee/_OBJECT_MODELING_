package com.crio.jukebox.exceptions;

public class InvalidPlayListOperationException extends RuntimeException {

    public InvalidPlayListOperationException() {
        super();
    }

    public InvalidPlayListOperationException(String msg) {
        super(msg);
    }
}