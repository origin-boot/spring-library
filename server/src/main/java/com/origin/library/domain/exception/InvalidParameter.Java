package com.origin.library.domain.exception;

import com.origin.library.domain.Error;

public class InvalidParameter extends Error {
        public InvalidParameter() {
                super(400, "Bad Request");
                this.status = HttpStatus.BAD_REQUEST.value();
        }
}