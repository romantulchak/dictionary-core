package com.dictionary.controller;

import com.dictionary.exception.file.AudioCreationException;
import com.dictionary.exception.file.FolderCreationException;
import com.dictionary.exception.language.LanguageAlreadyExistsException;
import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.user.UsernameAlreadyExistsException;
import com.dictionary.exception.word.WordAlreadyExistsException;
import com.dictionary.exception.word.WordTranslationNotFoundException;
import com.dictionary.exception.word.WordsAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(LanguageAlreadyExistsException.class)
    public ResponseEntity<?> handleLanguageAlreadyExistsException(LanguageAlreadyExistsException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(LanguageNotFoundException.class)
    public ResponseEntity<?> handleLanguageNotFoundException(LanguageNotFoundException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(WordAlreadyExistsException.class)
    public ResponseEntity<?> handleWordAlreadyExistsException(WordAlreadyExistsException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(AudioCreationException.class)
    public ResponseEntity<?> handleAudioCreationException(AudioCreationException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(FolderCreationException.class)
    public ResponseEntity<?> handleFolderCreationException(FolderCreationException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(WordsAlreadyExistsException.class)
    public ResponseEntity<?> handleWordsAlreadyExistsException(WordsAlreadyExistsException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        body.put("words", ex.getWords());
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(WordTranslationNotFoundException.class)
    public ResponseEntity<?> handleWordTranslationNotFoundException(WordTranslationNotFoundException ex, WebRequest webRequest) {
        Map<String, Object> body = getBody(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            body.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    private Map<String, Object> getBody(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("date", LocalDateTime.now());
        return body;
    }
}
