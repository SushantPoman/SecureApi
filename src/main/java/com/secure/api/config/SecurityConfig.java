package com.secure.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authentication -> {
            String apiKey = (String) authentication.getPrincipal();
            String apiSecret = (String) authentication.getCredentials();

            if ("valid-api-key".equals(apiKey) && "valid-api-secret".equals(apiSecret)) {
                return new ApiKeyAuthenticationToken(apiKey, apiSecret, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

            } else {
                authentication.setAuthenticated(false);
            }

            return authentication;
        });
        
//        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
//        if (securityContextRepository == null) {
//        	filter.setSecurityContextRepository(new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()));
//        }

//        http
//        .csrf(csrf -> csrf.disable())
//		.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//		.authorizeHttpRequests((requests) -> requests
//			.requestMatchers("/api/**").authenticated()
//			.anyRequest().permitAll()
//		);
        
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests.requestMatchers("/api/**").permitAll()
        .anyRequest().authenticated());


        
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/api/**").authenticated()
//                                .anyRequest().permitAll()
//                )
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
