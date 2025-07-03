package com.trindadeisencoes.crm.config;

import com.trindadeisencoes.crm.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Login e cadastro público
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                //Swagger
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // GESTOR acessa todos os usuários
                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority("GESTOR")

                // CLIENTES
                .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyAuthority("VENDEDOR", "GESTOR")
                .requestMatchers(HttpMethod.POST, "/api/clientes/**").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasAuthority("GESTOR")

                // PRODUTOS
                .requestMatchers(HttpMethod.GET, "/api/produtos/**").hasAnyAuthority("VENDEDOR", "GESTOR")
                .requestMatchers(HttpMethod.POST, "/api/produtos").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/produtos/**").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/produtos/**").hasAuthority("GESTOR")

                // VENDAS
                .requestMatchers(HttpMethod.GET, "/api/vendas/**").hasAnyAuthority("VENDEDOR", "GESTOR")
                .requestMatchers(HttpMethod.POST, "/api/vendas").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/api/vendas/**").hasAuthority("VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/api/vendas/**").hasAuthority("GESTOR")

                // Regra genérica para segurança
                .requestMatchers("/api/**").hasAuthority("GESTOR")

                // Todas as outras rotas exigem autenticação
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}