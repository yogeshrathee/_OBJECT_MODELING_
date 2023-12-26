package com.crio.jukebox.exceptions;

public class InvalidAlbumOperation extends RuntimeException {
    public InvalidAlbumOperation() {
        super();
    }

    public InvalidAlbumOperation(String msg) {
        super(msg);
    }
}