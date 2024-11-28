package example.goline.exception;

import example.goline.utils.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class HandlerGlobalException {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionMessage> SQLExceptionHandler(SQLException exception,
                                                                WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> customExceptionHandler(CustomException exception, WebRequest request) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .description(request.getDescription(true))
                .build();
        return new ResponseEntity<>(exceptionMessage, exception.getHttpStatus());
    }
}
