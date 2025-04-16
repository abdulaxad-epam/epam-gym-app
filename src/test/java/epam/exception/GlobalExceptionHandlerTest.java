package epam.exception;

import epam.exception.exception.*;
import epam.exception.exception_handler.ExceptionMessage;
import epam.exception.exception_handler.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleTraineeNotFound() {
        String message = "Trainee not found";
        TraineeNotFoundException exception = new TraineeNotFoundException(message);

        ResponseEntity<ExceptionMessage> response = handler.handleTraineeNotFound(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    void testHandleTrainerNotFound() {
        TrainerNotFoundException exception = new TrainerNotFoundException("Trainer missing");
        ResponseEntity<ExceptionMessage> response = handler.handleTrainerNotFound(exception);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Trainer missing", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainingNotFound() {
        TrainingNotFoundException exception = new TrainingNotFoundException("Training not found");
        ResponseEntity<ExceptionMessage> response = handler.handleTrainingNotFound(exception);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Training not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTraineeHasNotAssignedBefore() {
        TraineeHasNotAssignedBeforeException exception = new TraineeHasNotAssignedBeforeException("Not assigned before");
        ResponseEntity<ExceptionMessage> response = handler.handleTraineeHasNotAssignedBefore(exception);
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Not assigned before", response.getBody().getMessage());
    }

    @Test
    void testHandleUserNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User does not exist");
        ResponseEntity<ExceptionMessage> response = handler.handleUserNotFound(exception);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User does not exist", response.getBody().getMessage());
    }

    @Test
    void testHandleInvalidTokenType() {
        InvalidTokenType exception = new InvalidTokenType("Invalid token type");
        ResponseEntity<ExceptionMessage> response = handler.handleInvalidTokenType(exception);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid token type", response.getBody().getMessage());
    }

    @Test
    void testHandleUsernameGenerate() {
        UsernameGenerateException exception = new UsernameGenerateException("Username generation failed", any(IllegalAccessException.class));
        ResponseEntity<ExceptionMessage> response = handler.handleUsernameGenerate(exception);
        assertEquals(303, response.getStatusCodeValue());
        assertEquals("Username generation failed", response.getBody().getMessage());
    }

    @Test
    void testHandleUserNotAuthenticated() {
        UserNotAuthenticated exception = new UserNotAuthenticated("User not authenticated");
        ResponseEntity<ExceptionMessage> response = handler.handleUserNotAuthenticated(exception);
        assertEquals(403, response.getStatusCodeValue());
        assertEquals("User not authenticated", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainingTypeNotFound() {
        TrainingTypeNotFoundException exception = new TrainingTypeNotFoundException("Type not found");
        ResponseEntity<ExceptionMessage> response = handler.handleTrainingTypeNotFound(exception);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Type not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTraineeHasAssignedBefore() {
        TraineeHasAssignedBeforeException exception = new TraineeHasAssignedBeforeException("Already assigned");
        ResponseEntity<ExceptionMessage> response = handler.handleTraineeHasAssignedBefore(exception);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Already assigned", response.getBody().getMessage());
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(null, new BindException(new Object(), "object"));
        exception.getBindingResult().addError(fieldError);

        ResponseEntity<ExceptionMessage> response = handler.handleMethodArgumentNotValid(exception);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("must not be null"));
    }

//    @Test
//    void testHandleConstraintViolation() {
//        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
//        when(violation.getPropertyPath()).thenReturn(() -> "field");
//        when(violation.getMessage()).thenReturn("must not be empty");
//        Set<ConstraintViolation<?>> violations = new HashSet<>();
//        violations.add(violation);
//
//        ConstraintViolationException exception = new ConstraintViolationException(violations);
//
//        ResponseEntity<ExceptionMessage> response = handler.handleConstraintViolation(exception);
//        assertEquals(400, response.getStatusCodeValue());
//        assertTrue(response.getBody().getMessage().contains("must not be empty"));
//    }

    @Test
    void testHandleJsonParsingException() {
        Exception cause = new RuntimeException("Unrecognized date format");
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Invalid JSON", cause);

        ResponseEntity<ExceptionMessage> response = handler.handleJsonParsingException(exception);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unrecognized date format"));
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ExceptionMessage> response = handler.handleException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}
