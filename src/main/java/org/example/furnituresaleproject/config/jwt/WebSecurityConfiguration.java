package org.example.furnituresaleproject.config.jwt;

import org.example.furnituresaleproject.service.AccountService.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration{

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http)
//            throws Exception {
//
//        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
//    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource());
        http.csrf(i-> i.disable());
        http.sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http
                .authorizeRequests(authorizeRequests ->   authorizeRequests // bắt đầu config author
                                .requestMatchers("/auth/**", "/").permitAll() // cho phép tất cả user đã login và vô danh  truy cập vào endpoint mà không cần login với các url /(localhost:8080) hoặc /login
                                .anyRequest().authenticated()) // tất cả các url còn lại đều phải login
//                .httpBasic(httpSecurityHttpBasicConfigurer -> {})
//                .csrf(AbstractHttpConfigurer::disable) // disable csrf   cần thiết cho lưu trữnguoiwfif dưới dạng session
//                .addFilterBefore(
//                        new JwtAuthenticationFilter("/api/v1/login", authenticationManager(http)),
//                                UsernamePasswordAuthenticationFilter.class
//                ) // bộ lọc này xử lí đăng nhập khi nguoì dùng login thành công và tạo jwt toke
//                .addFilterBefore(
//                        new JwtAuthorizationFilter(),
//                        UsernamePasswordAuthenticationFilter.class
//                )// được dùng để kiểm tra jwt token trong các request đến và xác thực người dùng
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


//    }
}
