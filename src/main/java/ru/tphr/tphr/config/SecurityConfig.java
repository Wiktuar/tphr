// https://ru.stackoverflow.com/questions/545859/spring-security-%D0%9F%D1%80%D0%B8-%D0%B2%D0%B2%D0%BE%D0%B4%D0%B5-%D0%BB%D0%BE%D0%B3%D0%B8%D0%BD%D0%B0-%D0%B8-%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8F-%D0%B2%D1%8B%D0%BA%D0%B8%D0%B4%D1%8B%D0%B2%D0%B0%D0%B5%D1%82-%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D1%83-reason-user-is-dis
// эта информация поможет оптимизировать запросы при аутентификации пользователя
// https://stackoverflow.com/questions/56566406/spring-security-differentiate-between-wrong-username-password-combination-and
// https://docs.spring.io/spring-security/site/docs/4.1.3.RELEASE/guides/html5/form-javaconfig.html

package ru.tphr.tphr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ru.tphr.tphr.services.AuthorService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/img/**", "/reset/*", "/registration", "/check/**", "/static/**", "/activate/*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(authorService);
        return authenticationProvider;
    }
}
