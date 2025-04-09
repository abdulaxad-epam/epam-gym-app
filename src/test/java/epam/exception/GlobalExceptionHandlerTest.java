package epam.exception;

import epam.exception.exception.TraineeHasAssignedBeforeException;
import epam.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.exception.exception.TrainingNotFoundException;
import epam.exception.exception.TrainingTypeNotFoundException;
import epam.exception.exception.UserNotAuthenticated;
import epam.exception.exception.UserNotFoundException;
import epam.exception.exception.UsernameGenerateException;
import epam.exception.exception_handler.ExceptionMassage;
import epam.exception.exception_handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

        ResponseEntity<ExceptionMassage> response = handler.handleTrainerNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trainer not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainingNotFound() {
        TrainingNotFoundException exception = new TrainingNotFoundException("Training not found");

        ResponseEntity<ExceptionMassage> response = handler.handleTrainingNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Training not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTraineeHasNotAssignedBefore() {
        TraineeHasNotAssignedBeforeException exception = new TraineeHasNotAssignedBeforeException("Trainee was never assigned");

        ResponseEntity<ExceptionMassage> response = handler.handleTraineeHasNotAssignedBefore(exception);

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
    void testHandleUsernameGenerateException() {
        UsernameGenerateException exception = new UsernameGenerateException("Failed to generate username", new IllegalAccessException());

        ResponseEntity<ExceptionMassage> response = handler.handleUsernameGenerate(exception);

        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
        assertEquals("Failed to generate username", response.getBody().getMessage());
    }

    @Test
    void testHandleTrainingTypeNotFound() {
        TrainingTypeNotFoundException exception = new TrainingTypeNotFoundException("Training type not found");
        ResponseEntity<ExceptionMassage> response = handler.handleTrainingTypeNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Training type not found", response.getBody().getMessage());
    }

    @Test
    void testHandleTraineeHasAssignedBefore() {
        TraineeHasAssignedBeforeException exception = new TraineeHasAssignedBeforeException("Trainee already assigned");
        ResponseEntity<ExceptionMassage> response = handler.handleTraineeHasAssignedBefore(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Trainee already assigned", response.getBody().getMessage());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "must not be blank");
        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<ExceptionMassage> response = handler.handleMethodArgumentNotValid(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("must not be blank"));
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
