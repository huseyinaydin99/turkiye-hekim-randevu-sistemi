package tr.com.huseyinaydin.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import tr.com.huseyinaydin.security.AppUserDetailsService;
import tr.com.huseyinaydin.security.filters.CustomAuthenticationFilter;
import tr.com.huseyinaydin.services.AppUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // AuthenticationManager'ı önce oluştur
        /*AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();*/

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF'yi cookie ile tutuyoruz
                )
                //.csrf(AbstractHttpConfigurer::disable) // CSRF korumasını devre dışı bırak (gerekiyorsa) asla önermem!
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/ulogin/**", "/errorPage/**", "/login-success?nameSurname=*", "/clogin/**", "/images/**", "/css/**", "/js/**", "/api/v1/appointments/search/**", "/reset-password/**", "/forgot-password/**", "/account-confirm/**").permitAll()
                        //.requestMatchers(HttpMethod.POST,"/appointments/**").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/clogin")
                        //.loginProcessingUrl("/clogin") // Custom login post URL
                        .usernameParameter("email") // input name="email"
                        .passwordParameter("password") // input name="password"
                        .defaultSuccessUrl("/appointments/search", true)
                        .failureUrl("/errorPage")
                        //.failureHandler(authenticationFailureHandler())  // Hata mesajı için failure handler ekliyoruz
                        .permitAll()
                )
                //.addFilterBefore(new CustomAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/clogin?logout")
                        .invalidateHttpSession(true)
                        //.deleteCookies("JSESSIONID")
                        .permitAll()
                )
                /*.formLogin(form -> form
                        .loginPage("/ulogin")
                        .permitAll()
                )*/
                .sessionManagement(session -> session
                        //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Oturum yalnızca gerektiğinde oluşturulur
                        .sessionFixation().migrateSession()
                        .maximumSessions(1) // Kullanıcı başına maksimum oturum sayısı
                        .maxSessionsPreventsLogin(false) // Yeni oturum açıldığında eski oturumu sonlandır
                        .expiredUrl("/login?expired")
                );

        http.authenticationProvider(authenticationProvider());

        // Custom filter'ı ekleyelim
                /*http.addFilterBefore(customAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)),
                        UsernamePasswordAuthenticationFilter.class);*/

        return http.build();
    }

    /*@Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/clogin"); // Login işlemi için URL
        return filter;
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(); // senin kendi UserDetailsService implementasyonun
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    /*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(appUserService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }*/

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}