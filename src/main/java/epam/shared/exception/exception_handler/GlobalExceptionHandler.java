package epam.shared.exception.exception_handler;

import epam.shared.exception.exception.DateConversionException;
import epam.shared.exception.exception.EntityManagerInsertException;
import epam.shared.exception.exception.TraineeHasAssignedBeforeException;
import epam.shared.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.exception.exception.TrainingNotFoundException;
import epam.shared.exception.exception.TrainingTypeNotFoundException;
import epam.shared.exception.exception.UserNotAuthenticated;
import epam.shared.exception.exception.UserNotFoundException;
import epam.shared.exception.exception.UsernameGenerateException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TraineeNotFoundException.class)
    public ResponseEntity<ExceptionMassage> handleTraineeNotFound(TraineeNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ExceptionMassage> handleTraineeNotFound(TrainerNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(TrainingNotFoundException.class)
    public ResponseEntity<ExceptionMassage> handleTraineeNotFound(TrainingNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(TraineeHasNotAssignedBeforeException.class)
    public ResponseEntity<ExceptionMassage> handleTraineeNotFound(TraineeHasNotAssignedBeforeException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(EntityManagerInsertException.class)
    public ResponseEntity<ExceptionMassage> handleTraineeNotFound(EntityManagerInsertException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMassage> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(DateConversionException.class)
    public ResponseEntity<ExceptionMassage> handleDateConversion(DateConversionException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameGenerateException.class)
    public ResponseEntity<ExceptionMassage> handleUsernameGenerateException(UsernameGenerateException e) {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.SEE_OTHER.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotAuthenticated.class)
    public ResponseEntity<ExceptionMassage> handleUserNotAuthenticated(UserNotAuthenticated e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(TrainingTypeNotFoundException.class)
    public ResponseEntity<ExceptionMassage> handleTrainingTypeNotFound(TrainingTypeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(TraineeHasAssignedBeforeException.class)
    public ResponseEntity<ExceptionMassage> handleTrainingTypeNotFound(TraineeHasAssignedBeforeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(e.getMessage())
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMassage> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionMassage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errors.values().toString())
                        .build()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMassage> handleConstraintViolation(ConstraintViolationException e) {

        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionMassage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errors.values().toString())
                        .build()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionMassage> handleJsonParsingException(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid date format: " + e.getMostSpecificCause().getMessage() +". Must be yyyy-MM-dd");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionMassage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(error.values().toString())
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMassage> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionMassage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(e.getMessage())
                        .build()
                );
    }
}
