package com.dev.ep02joaquinmoreno.configuration;
import lombok.Data;

@Data
public class JwtException extends RuntimeException {

    private final int code;
    private final String message;


    public JwtException(String message, int code) {
        super();
        this.code= code;
        this.message= message;
    }


}
