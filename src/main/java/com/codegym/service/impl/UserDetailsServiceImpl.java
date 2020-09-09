package com.codegym.service.impl;

import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        try {
            user = userRepository.findByUserName(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Name " + username + "not found");
        }
//        User user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Name " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword() ,
                getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Role role = user.getRole();
        List<String> roleList = new ArrayList<>();
        roleList.add(role.getName());
        String[] userRoles = new String[1];
        roleList.toArray(userRoles);
//        String[] userRoles = user.getRole().stream().map((role) -> role.getName()).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
//        return authorities;
    }
}
