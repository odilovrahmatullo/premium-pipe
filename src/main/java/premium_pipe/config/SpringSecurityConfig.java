package premium_pipe.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Order(1)
    @Configuration
    @RequiredArgsConstructor
    public static class ApiSecurityConfig {
        private final JwtAuthFilter jwtAuthFilter;

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.securityMatcher("/api/**")
            .authorizeHttpRequests(auth ->
                                 auth
                                .requestMatchers(
                                        "/admin/login",
                                        "/api/login/**",
                                        "/api/gallery/**",
                                        "/api/file/**",
                                        "/api/files/**",
                                        "/api/category/**",
                                        "/api/product/**",
                                        "/api/news/**",
                                        "/uploads/**",
                                        "/api/contact/**",
                                        "/api/language/**",
                                        "/api/translation",
                                        "/api/banner",
                                        "/api/about",
                                        "/api/gallery"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .formLogin(AbstractHttpConfigurer::disable)
                    .anonymous(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(
                            handling ->
                                    handling
                                            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                            .accessDeniedHandler(
                                                    (request, response, accessDeniedException) ->
                                                            response.setStatus(HttpServletResponse.SC_FORBIDDEN)));
            return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowCredentials(true);
            configuration.setAllowedOrigins(
                    List.of(
                            "http://localhost:8080",
                            "http://localhost:3000",
                            "https://premium-pipe.netlify.app"));
            configuration.addAllowedMethod("*");
            configuration.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }

    @Order(2)
    @Configuration
    @RequiredArgsConstructor
    public static class AdminSecurityConfig {
        private final UserDetailsService adminDetailsService;
        private final BCryptPasswordEncoder passwordEncoder;

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(adminDetailsService);
            return provider;
        }

        @Bean
        public SecurityFilterChain filterChainAdmin(final HttpSecurity http) throws Exception {
            http.securityMatcher("/admin/**")
                    .authorizeHttpRequests(
                            auth ->
                                    auth.requestMatchers(
                                                    "/api/files/**",
                                                    "/admin/assets/**",
                                                    "/admin/src/js/**",
                                                    "/uploads/**",
                                                    "/css/**",
                                                    "/js/**",
                                                    "/admin/login",
                                                    "/error")
                                            .permitAll()
                                            .requestMatchers(
                                                    "/admin/gallery/**",
                                                    "/admin/category/**",
                                                    "/admin/product/**",
                                                    "/admin/news/**",
                                                    "/admin/contact/**",
                                                    "/admin/language/**",
                                                    "/admin/about/**",
                                                    "/admin/partner/**",
                                                    "/admin/banner/**",
                                                    "/admin/type/**",
                                                    "/admin/translation/**"
                                            ).hasRole("ADMIN")
                    )
                    .formLogin(
                            formLogin ->
                                    formLogin
                                            .loginPage("/admin/login")
                                            .loginProcessingUrl("/admin/login")
                                            .defaultSuccessUrl("/admin/language", true)
                                            .failureUrl("/admin/login?error=loginError"))
                    .logout((logout) -> logout.logoutSuccessUrl("/admin/login").logoutUrl("/admin/logout"))
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .csrf(
                            csrf ->
                                    csrf.ignoringRequestMatchers("/api/**")
                                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

            return http.build();
        }

    }

}
