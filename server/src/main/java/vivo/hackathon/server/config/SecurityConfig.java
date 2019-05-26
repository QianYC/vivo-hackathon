package vivo.hackathon.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Order(3)
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .anyRequest().permitAll();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        return new UserAuthProvider();
    }

    @Configuration
    @Order(1)
    public class UserSecurity extends WebSecurityConfigurerAdapter{
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .antMatcher("/user/**")
                    .authorizeRequests()
                    .antMatchers("/register").permitAll()
                    .anyRequest().hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/user/login")/*.successHandler(null)*/.permitAll();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
            /**
             * 验证方式为根据数据库内容验证
             */
            builder.authenticationProvider(authenticationProvider());
        }
    }

    @Configuration
    @Order(2)
    public class AdminSecurity extends WebSecurityConfigurerAdapter{
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/register").permitAll()
                    .anyRequest().hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/admin/login")/*.successHandler(null)*/.permitAll();

        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
            builder.authenticationProvider(authenticationProvider());
        }
    }
}
