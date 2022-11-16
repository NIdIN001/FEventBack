package ru.nsu.fevent.controller

import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.nsu.fevent.dto.ErrorStatus
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.exception.*

@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(RegistrationException::class)
    fun registrationExceptionHandler(exception: RegistrationException): Response<Nothing> {
        return Response.withError(exception.message)
    }

    @ExceptionHandler(AuthException::class)
    fun authExceptionHandler(exception: AuthException): Response<Nothing> {
        return Response.withError(ErrorStatus.AUTH_ERROR, exception.message)
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

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundExceptionHandler(exception: UserNotFoundException): Response<Nothing> {
        return Response.withError(exception.message)
    }

    @ExceptionHandler(FriendException::class)
    fun friendExceptionHandler(exception: FriendException): Response<Nothing> {
        return Response.withError(exception.message)
    }

    @ExceptionHandler(PasswordException::class)
    fun passwordExceptionHandler(exception: PasswordException): Response<Nothing> {
        return Response.withError(exception.message)
    }
}