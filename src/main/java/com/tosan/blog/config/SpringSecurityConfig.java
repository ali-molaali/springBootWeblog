package com.tosan.blog.config;

import com.tosan.blog.moudules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService usersService;
    @Autowired
    public SpringSecurityConfig(UserService usersService) {
        this.usersService = usersService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(new BCryptPasswordEncoder());
//                .jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .usersByUsernameQuery("select email,password,enabled from users_tbl where email=?")
//                .authoritiesByUsernameQuery("select email,role from user_role where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
               .antMatchers("/", "/index", "/css/**", "/js/**", "/img/**","/users/**","/webjars/**")
                .permitAll()
                //.antMatchers("/categories/**","/posts/**")
                //.hasAuthority("ADMIN")
                //.antMatchers("/posts/**")
                //.hasAuthority("USER")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").usernameParameter("email")
                .permitAll().and().logout().logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/notaccess");
    }
}
