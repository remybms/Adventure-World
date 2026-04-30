package com.ynov.fantasy_war.infra.api;

import com.ynov.fantasy_war.domain.NotFoundException;
import com.ynov.fantasy_war.domain.RequeteInvalideException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Requête invalide");
        problem.setDetail("Un ou plusieurs champs sont invalides");

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (a, b) -> a
                ));

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(RequeteInvalideException.class)
    public ProblemDetail handleRequeteInvalideException(
            RequeteInvalideException ex) {
        return buildProblemDetail(ex, HttpStatus.BAD_REQUEST, "Requête invalide");
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(
            NotFoundException ex) {
        return buildProblemDetail(ex, HttpStatus.NOT_FOUND, "Données non trouvée invalide");
    }

    private static @NonNull ProblemDetail buildProblemDetail(Exception ex, HttpStatus httpStatus, String title) {
        ProblemDetail problem = ProblemDetail.forStatus(httpStatus);
        problem.setTitle(title);
        problem.setDetail(ex.getMessage());
        return problem;
    }
}

