package ru.nsu.fevent.controller.interceptor

import mu.KotlinLogging
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggerInterceptor : HandlerInterceptor {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        /* TODO переписать логирование на spring фильтры и добавить logback.xml */
        logger.info { "Получен запрос [${request.method} ${request.servletPath}]" }
        return true
    }
}
