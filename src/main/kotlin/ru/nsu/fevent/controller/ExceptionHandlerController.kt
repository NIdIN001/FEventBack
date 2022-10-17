package ru.nsu.fevent.controller

import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.exception.RegistrationException

@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(RegistrationException::class)
    fun registrationExceptionHandler(exception: RegistrationException): Response<Nothing> {
        return Response.withError(exception.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun registrationExceptionHandler(exception: MethodArgumentNotValidException): Response<Nothing> {
        val fieldErrors: List<FieldError> = exception.fieldErrors

        val errorMessage = fieldErrors.stream()
            .map { fe -> fe.defaultMessage }
            .toList()
            .getOrElse(0) { "Неизвестная ошибка" }

        return Response.withError(errorMessage)
    }
}