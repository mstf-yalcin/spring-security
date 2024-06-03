package com.security.basic.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");
            return errorDetail;
        }
        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Access is denied");
            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
            return errorDetail;
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
            return errorDetail;
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
            return errorDetail;
        }

        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty("description", "Unknown internal server error.");
        return errorDetail;

    }

//
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<?> handleJwtException(JwtException ex) {
//        ArrayList<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex) {
//        ArrayList<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
//        ArrayList<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<?> malformedJwtException(MalformedJwtException ex) {
//        ArrayList<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

}
