package com.crio.jukebox.exceptions;

public class InvalidSongOperationException extends RuntimeException {
    public InvalidSongOperationException() {
        super();
    }

    public InvalidSongOperationException(String msg) {
        super(msg);
    }
}