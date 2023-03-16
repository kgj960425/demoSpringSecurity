package com.example.demospringsecurity.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
//    기본 의존성 주입법
//    @Autowired
//    private AccountRepository accountR;

//    spring5부터 가능한 의존성 주입법
//    조건1. 생성자가 1개만 있음
//    조건2. 그 생성자의 매개변수들이 bean이 등록되어 있을 때

    @Autowired
    private AccountRepository accountR;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    거의 대부분의 웹사이트에서 사용되는 도메인이 가지고 있을법한 properties를 추상화 해놓은게 userDetails라는 인터페이스입니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountR.findByEmail(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(account.getEmail(), account.getPassword(), authorities);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new User(account.getEmail(), account.getPassword(), authorities);
//        위의 3줄이 아래의 메소드를 대체함.
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//
//              List<GrantedAuthority> authorities = new ArrayList<>();
//              authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//                return authorities;
//            }
//
//            @Override
//            public String getPassword() {
//                return account.getPassword();
//            }
//
//            @Override
//            public String getUsername() {
//                return account.getEmail();
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return true;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return true;
//            }
//        };
//        return userDetails;
    }

    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountR.save(account);
    }
}
