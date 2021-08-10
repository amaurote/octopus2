package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.Role;
import com.amaurote.octopus2.domain.entity.User;
import com.amaurote.octopus2.dto.UserDTO;
import com.amaurote.octopus2.dto.UserRegistrationDTO;
import com.amaurote.octopus2.exception.RoleException;
import com.amaurote.octopus2.repository.RoleRepository;
import com.amaurote.octopus2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createNewUser(UserRegistrationDTO dto) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleException("Role not found.")));

        User user = User.builder()
                .userName(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .active(true)
                .enabled(true)
                .loginByEmail(true)
                .build();

        return userRepository.save(user);
    }

    private UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .loginByEmail(user.isLoginByEmail())
                .build();
    }
}
