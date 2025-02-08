package premium_pipe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Order(1)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                            "/admin/login",
                            "/api/login/**",
                            "/api/gallery/**",
                            "/api/file/**",
                            "/api/files/**",
                            "/api/category/**",
                            "/api/product/**",
                            "/api/news/**",
                            "/api/contact/**",
                            "/api/language/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        }).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
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
                                                    "/admin/partner/**"
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
