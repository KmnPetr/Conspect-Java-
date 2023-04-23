package com.example.FirstSecurityApp.config;

import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig /*extends WebSecurityConfigurerAdapter устарело все*/{
    //нужно при использовании AuthProvider
    /*private final AuthProviderImpl authProvider;
    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }*/
    private final PersonDetailsService personDetailsService;
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        System.out.println("сработал конструктор SecurityConfig");
        this.personDetailsService = personDetailsService;
    }
    //устарело, применялось при наследовании WebSecurityConfigurerAdapter
    /*@Override
    protected void configure(HttpSecurity http)throws Exception{
        //конфигурируем сам спринг секьюрити
        //конфигурируем авторизацию
        http.authorizeHttpRequests*//*неуверен*//*()//настройка авторизации
                .requestMatchers("/auth/login","/error").permitAll()//на эти 2 странички пускаем всех пользователей
                .anyRequest().authenticated()//на все остальные запросы он должен быть аутентифицирован
                .and()
                .formLogin()
                .loginPage("/auth/login")//другую страничку для логина
                .loginProcessingUrl("/process_login")//сюда прийдут данные с формы можно написать любой другой адрес,на форме он тоже обозначен
                .defaultSuccessUrl("/hello",true)//url после успешной аутентификации
                .failureUrl("/auth/login?error");//в случае неуспешной аутентификации url с параметром ошибки

    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests/*неуверен*/()//настройка авторизации
                .requestMatchers("/auth/login","auth/registration","/error").permitAll()//на эти 2 странички пускаем всех пользователей
                .anyRequest().authenticated()//на все остальные запросы он должен быть аутентифицирован
                .and()
                .formLogin()
                .loginPage("/auth/login")//другую страничку для логина
                .loginProcessingUrl("/process_login")//сюда прийдут данные с формы можно написать любой другой адрес,на форме он тоже обозначен
                .defaultSuccessUrl("/hello",true)//url после успешной аутентификации
                .failureUrl("/auth/login?error");//в случае неуспешной аутентификации url с параметром ошибки
        return http.build();
    }

    //настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);//нужен при использовании AuthProvider
        auth.userDetailsService(personDetailsService);//спринг сам сравнит пароли
    }

    /**
     * укажет каким способом шифруется палоль
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();//никак не шифруем пока
    }
}
