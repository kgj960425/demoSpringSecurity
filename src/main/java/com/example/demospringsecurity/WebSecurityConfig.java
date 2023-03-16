package com.example.demospringsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
//@EnableWebSecurity를 쓰는 순간 springBoot가 제공해주는 기본적인 시큐리티는 날아가고 시큐리티 및 접속 로직을 사용자가 직접 작성하게된다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        //requestMatchers로 인증 없이 로그인 없이도 들어갈 수 있는 화면 작성
                        .requestMatchers("/", "/home","/create").permitAll()
                                //.requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        //url이 들어오면 requestMatchers에서 확인한다. 그리고 여기서 걸리지 않는 url들은 인증이 필요하기에 .anyRequest().authenticated()를 타게 되고
                        //FilterBean이 가로채서 formLogin()으로 보낸다.form 로그인으로 발싸하면 form 로그인 에서 설정된 loginPage에서 받는다.
                )
                //loginForm 설정
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .and()
                )
                .logout((logout) -> logout
                        //.logoutUrl("/home")
                        //로그 아웃 끝난 다음에(성공한 다음에) 이동 시켜주는 페이지 설정 - logoutSuccessUrl
                        .logoutSuccessUrl("/home")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//        UserDetails는 사용자의 정보를 담는 인터페이스로
//        getAuthorities() 계정의 권한 목록을 리턴
//        getPassword() 계정의 비밀번호를 리턴
//        getUsername() 계정의 고유한 값을 리턴(db에서의 pk값)
//        isAccountNonExpired() 계정의 만료여부 리턴
//        isAccountNonLocked() 계정의 잠김 여부 리턴
//        isCredentialsNonExpired() 비밀번호 만료 여부 리턴
//        isEnabled() 계정의 활성화 여부 리턴
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
////  name=userId, password, role을 셋팅할 수 있음.
//        return new InMemoryUserDetailsManager(user);
//    }

//    원래 유저는 화면에서 사용자의 입력을 받거나 form이나 restApi를 통해서 데이터가 들어오면 그런 데이터만 받는 controller가 있음
//    걔가 최초의 요청을 받아서 검증을 하고 parameter에 넣고 확인하고 유효한 데이터인지 확인하고
//    그 다음에 user객체를 만들어서 repository를 통해서 영속화를 저장을 한다.사용자가 로그인을 하려고 할때 그 영속화된 데이터를 읽어서 사용자가 로그인 하려고 했던 순간,
//    이 유저 id에 해당하는 유저 객체를 가져온다. 비밀번호가 맞으면 접속.
//    로그인이 완성되면 authentication이라는 객체를 sccurity context에 넣고 그 다음부터 무슨 요청이 오면 여기서 확인함.
}
