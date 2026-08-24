package com.web.lawyer_backend_system.exception;

import lombok.Getter;

@Getter
public class MissingDocumentStorageException extends RuntimeException {

    private final String storagePath;
    public MissingDocumentStorageException(String storagePath) {
        super("Document storage path is invalid or unwritable: " + storagePath);
        this.storagePath = storagePath;
    }
}
