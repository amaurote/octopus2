package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.MyUserDetails;
import com.amaurote.octopus2.domain.entity.Role;
import com.amaurote.octopus2.domain.entity.User;
import com.amaurote.octopus2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        //        Optional<User> user = userRepository.findByUserName(userName);
        Optional<User> user = userRepository.findUser(userNameOrEmail);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + userNameOrEmail));
        User loaded = user.get();

        return new MyUserDetails(loaded.getUserName(), loaded.getPassword(), mapRolesToAuthorities(loaded.getRoles())); // todo pass active and enabled
    }

    private Set<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
