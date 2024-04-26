package com.origin.library.restful;

public record BookResource(
        int id,
        String name,
        int borrowTime,
        int returnTime,
        int userId,
        int username) {

    public static BookResource of() {
        return new BookResource(0, "", 0, 0, 0, 0);
    }
}
