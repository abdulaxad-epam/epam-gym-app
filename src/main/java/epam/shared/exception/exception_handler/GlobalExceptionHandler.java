package epam.shared.exception.exception_handler;

import epam.shared.exception.exception.EntityManagerInsertException;
import epam.shared.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.exception.exception.TrainingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
