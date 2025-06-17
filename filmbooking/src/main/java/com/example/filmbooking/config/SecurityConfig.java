
package com.example.filmbooking.config;
import com.example.filmbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private UserRepository userRepository;
    public SecurityConfig(UserRepository userRepository) {
		    	this.userRepository =userRepository;
	}
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            return userRepository.findByUsername(username)
                    .map(user -> User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().replace("ROLE_", "")) 
                        .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf->csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/", "/js/").permitAll()
//                .requestMatchers("/admin/").hasRole("ADMIN")
                .requestMatchers("/admin/**").permitAll()
//                .requestMatchers("/user/").hasRole("USER")
                .requestMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/user/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );   
        return http.build();
    }
}