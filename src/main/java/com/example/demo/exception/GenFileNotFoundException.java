package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "genFile not found")
public class GenFileNotFoundException extends RuntimeException {
}

//파일을 찾을수 없을떄 runtimeException으로 처리