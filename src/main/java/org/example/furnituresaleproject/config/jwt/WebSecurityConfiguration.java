package org.example.furnituresaleproject.config.jwt;

import org.example.furnituresaleproject.service.AccountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration{

    @Autowired
    private AccountService service;
//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
//    }// má hóa mật khẩu và dùng IAccountService   để lấy thông tin người dung tu DB khi can để xác thuc nguoi dung



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(service).passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorizeRequests ->   authorizeRequests // bắt đầu config author
                                .requestMatchers("api/v1/login", "/").permitAll() // cho phép tất cả user đã login và vô danh  truy cập vào endpoint mà không cần login với các url /(localhost:8080) hoặc /login
                                .anyRequest().authenticated()) // tất cả các url còn lại đều phải login
                .httpBasic(httpSecurityHttpBasicConfigurer -> {})
                .csrf(AbstractHttpConfigurer::disable) // disable csrf   cần thiết cho lưu trữnguoiwfif dưới dạng session
                .addFilterBefore(
                        new JwtAuthenticationFilter("/api/v1/login", authenticationManager(http)),
                                UsernamePasswordAuthenticationFilter.class
                ) // bộ lọc này xử lí đăng nhập khi nguoì dùng login thành công và tạo jwt toke
                .addFilterBefore(
                        new JwtAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )// được dùng để kiểm tra jwt token trong các request đến và xác thực người dùng
                .build();
//        return http.build();
    }


//    }
}
