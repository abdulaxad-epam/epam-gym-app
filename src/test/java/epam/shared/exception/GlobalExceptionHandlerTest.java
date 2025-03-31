package epam.shared.exception;

import epam.shared.exception.exception.DateConversionException;
import epam.shared.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.exception.exception.TrainingNotFoundException;
import epam.shared.exception.exception.UserNotAuthenticated;
import epam.shared.exception.exception.UserNotFoundException;
import epam.shared.exception.exception.UsernameGenerateException;
import epam.shared.exception.exception_handler.ExceptionMassage;
import epam.shared.exception.exception_handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleTraineeNotFound() {
        TraineeNotFoundException exception = new TraineeNotFoundException("Trainee not found");

        ResponseEntity<ExceptionMassage> response = handler.handleTraineeNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trainee not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainerNotFound() {
        TrainerNotFoundException exception = new TrainerNotFoundException("Trainer not found");

        ResponseEntity<ExceptionMassage> response = handler.handleTraineeNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trainer not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainingNotFound() {
        TrainingNotFoundException exception = new TrainingNotFoundException("Training not found");

        ResponseEntity<ExceptionMassage> response = handler.handleTraineeNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Training not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTraineeHasNotAssignedBefore() {
        TraineeHasNotAssignedBeforeException exception = new TraineeHasNotAssignedBeforeException("Trainee was never assigned");

        ResponseEntity<ExceptionMassage> response = handler.handleTraineeNotFound(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Trainee was never assigned", response.getBody().getMessage());
    }

    @Test
    void testHandleUserNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<ExceptionMassage> response = handler.handleUserNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void testHandleDateConversionException() {
        DateConversionException exception = new DateConversionException("Invalid date format");

        ResponseEntity<ExceptionMassage> response = handler.handleDateConversion(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date format", response.getBody().getMessage());
    }

    @Test
    void testHandleUsernameGenerateException() {
        UsernameGenerateException exception = new UsernameGenerateException("Failed to generate username", new IllegalAccessException());

        ResponseEntity<ExceptionMassage> response = handler.handleUsernameGenerateException(exception);

        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
        assertEquals("Failed to generate username", response.getBody().getMessage());
    }

    @Test
    void testHandleUserNotAuthenticated() {
        UserNotAuthenticated exception = new UserNotAuthenticated("User is not authenticated");

        ResponseEntity<ExceptionMassage> response = handler.handleUserNotAuthenticated(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User is not authenticated", response.getBody().getMessage());
    }

    @Test
    void testHandleJsonParsingException() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getMostSpecificCause()).thenReturn(new RuntimeException("Invalid input"));

        ResponseEntity<ExceptionMassage> response = handler.handleJsonParsingException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Invalid date format"));
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ExceptionMassage> response = handler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}
