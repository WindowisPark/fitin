package com.fitin.auth.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fitin.shopping.exception.MemberNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return handleException(ex, "User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return handleException(ex, "Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class})
    public ResponseEntity<?> handleJwtException(Exception ex, WebRequest request) {
        return handleException(ex, "Invalid JWT", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(MemberNotFoundException ex, WebRequest request) {
        return handleException(ex, "Member not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<?> handleInvalidResetTokenException(InvalidResetTokenException ex, WebRequest request) {
        return handleException(ex, "Invalid reset token", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> handleTokenExpiredException(TokenExpiredException ex, WebRequest request) {
        return handleException(ex, "Token expired", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<?> handleEmailSendException(EmailSendException ex, WebRequest request) {
        return handleException(ex, "Email send failure", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({StackOverflowError.class, Exception.class})
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return handleException(ex, "An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> handleException(Exception ex, String message, HttpStatus status) {
        logException(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    private void logException(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        log.error("Exception occurred: {}", ex.getMessage());
        log.error("Stack trace: {}", stackTrace);
    }
}

class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}