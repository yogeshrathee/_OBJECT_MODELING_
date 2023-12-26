package com.crio.jukebox.dto;

import com.crio.jukebox.entities.User;

public class UserDeletionDto {
    private String userId;

    public UserDeletionDto(String userId) {
        this.userId = userId;

    }

    public UserDeletionDto(User value, boolean b) {}

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserDeletionDto [user with id =" + userId + "]";
    }
}