package com.almasoft.numberencoding;

import java.io.IOException;

public class EncodingException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public EncodingException(IOException e) {
        super(e);
        
    }
}