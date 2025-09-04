package org.example.capstoneproject.exceptions;

import org.example.capstoneproject.dtos.ErrorDto;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ErrorDto handleNullPointerException(NullPointerException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage("Null Pointer Exception");
        return errorDto;
    }

}
