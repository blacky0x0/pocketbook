package com.github.blacky0x0.pocketbook.exception;

import com.github.blacky0x0.pocketbook.model.Contact;
import com.github.blacky0x0.pocketbook.model.PocketBook;

/**
 * User: blacky
 * Date: 12.04.15
 */
public class AppException extends RuntimeException {
    private PocketBook pocketBook = null;
    private Contact contact = null;
    private String uuid = null;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable e) {
        super(message, e);
    }

    public AppException(String message, PocketBook pocketBook) {
        super(message);
        this.pocketBook = pocketBook;
    }

    public AppException(String message, PocketBook pocketBook, Throwable e) {
        super(message, e);
        this.pocketBook = pocketBook;
    }

    public AppException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public AppException(String message, Contact contact) {
        super(message);
        this.contact = contact;
    }


    public PocketBook getPocketBook() {
        return pocketBook;
    }

    public Contact getContact() {
        return contact;
    }

    public String getUuid() {
        return uuid;
    }
}
