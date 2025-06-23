package com.ecommerce.ecommerce_api.config;


import com.ecommerce.ecommerce_api.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // constructor injection for required dependencies
    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**")
//                )
//                .headers(headers -> headers
//                        .frameOptions((frame -> frame.sameOrigin())
//                ));
        http
                // disable CSRF (not needed for stateless JWT)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable())

                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (everyone can access)
                        .requestMatchers("/api/user/register", "/api/user/login", "/api/user/generateToken",
                                "/h2-console/**",
                                "/api/products/**").permitAll()

                        // this line will be used for all /api/cart/** requests
                        .requestMatchers("/api/cart/**", "/api/checkout").hasRole("USER")
                )

                // stateless session (required for JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // set custom authentication provider
                .authenticationProvider(authenticationProvider())

                // add JWT filter before Spring Security's default filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * authentication provider configuration
     * links UserDetailsService and PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /*
     * authentication manager bean
     * required for programmatic authentication (e.g., in /generateToken)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
