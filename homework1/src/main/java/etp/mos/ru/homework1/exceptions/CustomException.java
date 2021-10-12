package etp.mos.ru.homework1.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

    private final HttpStatus resonseStatus;

    public CustomException(String message, HttpStatus resonseStatus) {
        super(message);
        this.resonseStatus = resonseStatus;
    }

    public HttpStatus getResonseStatus() {
        return resonseStatus;
    }

}
