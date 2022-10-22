package ru.nsu.fevent.controller.filter

import mu.KotlinLogging
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter
class LoggerFilter : OncePerRequestFilter() {

    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestCacheWrapper = ContentCachingRequestWrapper(request)
        val responseCacheWrapper = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()
        filterChain.doFilter(requestCacheWrapper, responseCacheWrapper)
        val endTime = System.currentTimeMillis()

        LOGGER.info {
            "Получен запрос [${requestCacheWrapper.method}] [${requestCacheWrapper.servletPath}]: " +
                    String(requestCacheWrapper.contentAsByteArray, Charsets.UTF_8)
        }

        LOGGER.info {
            "Ответ на запрос [${requestCacheWrapper.method}] [${requestCacheWrapper.servletPath}]: " +
                    String(responseCacheWrapper.contentAsByteArray, Charsets.UTF_8)
        }

        LOGGER.info {
            "Время обработки запроса [${requestCacheWrapper.method}] [${requestCacheWrapper.servletPath}]: " +
                    "${endTime - startTime} мс"
        }

        responseCacheWrapper.copyBodyToResponse()
    }
}
