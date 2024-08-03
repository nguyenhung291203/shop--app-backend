package com.example.shopappbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorException<T> {
    private Date timestamp;
    private T message;
    private String details;

    public ErrorException(T message, String details) {
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }
}
