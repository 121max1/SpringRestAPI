package com.ssu.maxshkodin.springrest.services;

import com.ssu.maxshkodin.springrest.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
public interface UserService extends UserDetailsService {

    void registerUser(User user, boolean isAdmin) throws  Exception;

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
