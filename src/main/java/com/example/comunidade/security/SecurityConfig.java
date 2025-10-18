package com.example.comunidade.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <<< AQUI
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
        		.requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll() // cadastro
                .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()        // listar serviços
                .requestMatchers(HttpMethod.POST, "/api/servicos/**").authenticated()   // criar só se logado
                .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasAnyRole("USER","ADMIN") // deletar
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults -> {});
        return http.build();
    }*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll()
	            .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/api/servicos/**").authenticated()
	            .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasAnyRole("USER","ADMIN")
	            .anyRequest().authenticated()
	        )
	        .httpBasic(Customizer.withDefaults()); // <--- aqui
	    return http.build();
	}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

