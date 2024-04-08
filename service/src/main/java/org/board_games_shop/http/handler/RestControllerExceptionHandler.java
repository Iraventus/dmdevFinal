package org.board_games_shop.http.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "org.board_games_shop.http.controller.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
