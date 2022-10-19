package ru.nsu.fevent.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.nsu.fevent.controller.interceptor.AuthenticationInterceptor
import ru.nsu.fevent.controller.interceptor.LoggerInterceptor

@Configuration
class MvcConfiguration(
    private val authenticationInterceptor: AuthenticationInterceptor,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)

        registry.addInterceptor(authenticationInterceptor)
            .excludePathPatterns("/**/register", "/**/login")
            .addPathPatterns("/**")

        registry.addInterceptor(LoggerInterceptor())
            .addPathPatterns("/**")
    }
}