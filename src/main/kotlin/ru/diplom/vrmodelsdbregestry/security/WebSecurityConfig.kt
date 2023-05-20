package ru.diplom.vrmodelsdbregestry.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.diplom.vrmodelsdbregestry.service.UserService


@Configuration
@EnableMethodSecurity
class WebSecurityConfig(
    private val userService: UserService,
    private val unauthorizedHandler: AuthEntryPointJwt
) {
    @Bean
    fun authenticationJwtTokenFilter(jwtUtils: JwtUtils) = AuthTokenFilter(
        userService = userService,
        jwtUtils = jwtUtils
    )

    @Bean
    fun authenticationProvider(
        passwordEncoder: PasswordEncoder
    ): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        authenticationJwtTokenFilter: AuthTokenFilter,
        authenticationProvider: DaoAuthenticationProvider
    ): SecurityFilterChain {
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .authorizeHttpRequests()
            .requestMatchers(
                "/auth/**",
                "/container",
                "/v3/**",
                "/swagger-ui.html",
                "/swagger-ui/**"
            ).permitAll()
            .anyRequest().authenticated()
        http.authenticationProvider(authenticationProvider)
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}