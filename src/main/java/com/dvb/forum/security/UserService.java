package com.dvb.forum.security;

import com.dvb.forum.entity.UserEntity;
import com.dvb.forum.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("UserService - loadUserByUsername - email: {}", email);

        UserEntity userEntity = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with supplied email."));
        log.info("UserService - loadUserByUsername - userEntity: {}", userEntity);

        User user = new User(userEntity.getEmailEntity().getEmailAddress(), userEntity.getPassword(), createGrantedAuthorities(userEntity));
        log.info("UserService - loadUserByUsername - user: {}", user);

        return user;
    }

    public UserEntity retrieveUserEntity(String email) throws UsernameNotFoundException {
        log.info("UserService - retrieveUserEntity - email: {}", email);

        UserEntity userEntity = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with supplied email."));
        log.info("UserService - retrieveUserEntity - userEntity: {}", userEntity);

        return userEntity;
    }

    private List<GrantedAuthority> createGrantedAuthorities(UserEntity userEntity) {
        log.info("UserService - createGrantedAuthorities - userEntity: {}", userEntity);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getUserRole().name()));
        log.info("UserService - createGrantedAuthorities - authorities: {}", authorities);

        return authorities;
    }

}
