package org.bgs.http.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "org.bgs.http.controller.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
