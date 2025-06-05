package vn.hoangdev.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.hoangdev.laptopshop.service.CustomUserDetailsService;
import vn.hoangdev.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)

public class SecurityConfiguration {
    // Cấu hình PasswordEncoder để mã hóa mật khẩu
    // Sử dụng BCryptPasswordEncoder để mã hóa mật khẩu
    // BCryptPasswordEncoder là một phần của Spring Security, nó sẽ mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ghi đè lại phương thức userDetailsService để sử dụng CustomUserDetailsService
    // thay vì UserDetailsService mặc định của Spring Security
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    // Cấu hình DaoAuthenticationProvider để sử dụng UserDetailsService và PasswordEncoder
    // DaoAuthenticationProvider là một phần của Spring Security, nó sẽ sử dụng
    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        // authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    // Cấu hình AuthenticationSuccessHandler để xử lý khi người dùng đăng nhập thành công
    // AuthenticationSuccessHandler là một phần của Spring Security, nó sẽ xử lý khi người dùng đăng nhập thành công
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }

    // Cấu hình SpringSessionRememberMeServices để sử dụng trong ứng dụng
    // SpringSessionRememberMeServices là một phần của Spring Session, nó sẽ xử lý việc ghi nhớ người dùng
    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    // Cấu hình bảo mật cho ứng dụng
    // Cho phép truy cập vào các đường dẫn cụ thể mà không cần xác thực
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE) // Cho phép các yêu cầu forward và include 
                            
                        .permitAll()
                        .requestMatchers("/", "/login", "/product/**", "/register", "/products/**",
                                "/client/**", "/css/**", "/js/**", "/images/**")
                        .permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                        .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Luôn tạo phiên làm việc mới
                        .invalidSessionUrl("/logout?expired") // Đường dẫn khi phiên hết hạn
                        .maximumSessions(1) // Giới hạn số phiên (số người) đăng nhập tối đa là 1
                        .maxSessionsPreventsLogin(false)) // Cho phép người dùng đăng nhập lại nếu phiên trước đó hết hạn

                    .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                    .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                    .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                    .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));
            return http.build();
    }
}
 