package com.crio.jukebox.exceptions;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException() {
        super();
    }

    public AlbumNotFoundException(String msg) {
        super(msg);
    }
}