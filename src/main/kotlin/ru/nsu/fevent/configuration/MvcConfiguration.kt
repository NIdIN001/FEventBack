package ru.nsu.fevent.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.nsu.fevent.controller.interceptor.AuthenticationInterceptor

@Configuration
@EnableWebMvc
class MvcConfiguration(
    private val authenticationInterceptor: AuthenticationInterceptor,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)

        /* Формирование защиненных ресурсов, доступ к которым разрешен только аутентифицированным пользователям */
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/event/view",
                "/event/choose/**",
                "/user/register",
                "/authenticate/login",
                "/actuator/**",
                "/city"
            )
    }
}
