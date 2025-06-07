package com.charbhujatech.cloudmart.exception;

import com.charbhujatech.cloudmart.dto.ExceptionResponseDTO;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDTO> handleExceptionPasswordMisMatchException(BadRequestException badRequestException)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(badRequestException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponseDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotCreated.class)
    public ResponseEntity<ExceptionResponseDTO> handleEntityNotCraeted(EntityNotCreated entityNotCreated)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(entityNotCreated.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(resourceNotFoundException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionResponseDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponseDTO> myAuthenticationException(AuthenticationException authenticationException
            ) {

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(authenticationException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJWTToken.class)
    public ResponseEntity<ExceptionResponseDTO> jwtExceptionResponseDTOResponseEntity(InvalidJWTToken invalidJWTToken) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(invalidJWTToken.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDTO> sqlIntegrityException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(sqlIntegrityConstraintViolationException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponseDTO> sqlIntegrityException(ExpiredJwtException expiredJwtException ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(expiredJwtException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClaimJwtException.class)
    public ResponseEntity<ExceptionResponseDTO> sqlIntegrityException(ClaimJwtException claimJwtException ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO();
        exceptionResponseDTO.setMessage(claimJwtException.getMessage());
        exceptionResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errorsMap= new HashMap<>();
        List<ObjectError> objectError = ex.getBindingResult().getAllErrors();

        objectError.forEach((error) -> {
            String fieldName = (( FieldError)error).getField();
            String msg= error.getDefaultMessage();
            errorsMap.put(fieldName,msg);
        });
        return new ResponseEntity<>(errorsMap,HttpStatus.BAD_REQUEST);
    }

}
