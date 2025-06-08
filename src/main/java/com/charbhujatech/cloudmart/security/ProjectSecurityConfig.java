package com.charbhujatech.cloudmart.security;

import com.charbhujatech.cloudmart.enums.Roles;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ProjectSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( (request) -> request
                        .requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/loginApp").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/users").hasAuthority(Roles.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/{userId}/addresses").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/addresses/{addressId}").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/addresses").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.PUT,"/api/v1/users/{userId}/addresses/{addressId}").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/{userId}/addresses/{addressId}").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/products").hasAnyAuthority(Roles.ADMIN.toString(),Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/products").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/products/{productId}/images").hasAnyAuthority(Roles.ADMIN.toString(),Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/products/{productId}/images").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/images/**").hasAnyAuthority(Roles.ADMIN.toString(),Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/cards/{cardId}/products/{productId}").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/cards/{cardId}/products/{productId}").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/cards/**").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/{userId}/orders").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/{userId}/orders/{orderId}").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/orders/{orderId}").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/orders").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/confirmed").hasAnyAuthority(Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/processing").hasAnyAuthority(Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/shipped").hasAnyAuthority(Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/outForDelivery").hasAnyAuthority(Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/delivered").hasAnyAuthority(Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/returnRequest").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/returned").hasAnyAuthority(Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/rejected").hasAnyAuthority(Roles.VENDOR.toString())
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{userId}/orders/{orderId}/refund").hasAnyAuthority(Roles.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/reviews").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/products/{productId}/reviews").hasAnyAuthority(Roles.ADMIN.toString(),Roles.CUSTOMER.toString(),Roles.VENDOR.toString(),Roles.DELIVERY_PARTNER.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/{userId}/orders/{orderId}/products/{productId}/reviews").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.POST,"/api/v1/users/{userId}/orders/{orderId}/payments").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/orders/{orderId}/payments/**").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .requestMatchers(HttpMethod.GET,"/api/v1/users/{userId}/payments").hasAnyAuthority(Roles.CUSTOMER.toString())
                        .anyRequest().denyAll()
        ).csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder)
    {
        ShoppingAuthenticationProvider shoppingAuthenticationProvider = new ShoppingAuthenticationProvider(userDetailsService,
                passwordEncoder);
        ProviderManager providerManager = new ProviderManager(shoppingAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
