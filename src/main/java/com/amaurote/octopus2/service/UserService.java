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

    public void test() {
        User admin = new User();
        admin.setEmail("admin@test.com");
        admin.setUserName("admin");
        admin.setPassword(passwordEncoder.encode("pass"));
        admin.setActive(true);
        admin.setEnabled(true);
        admin.setLoginByEmail(true);

        User user = new User();
        user.setEmail("user@test.com");
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setActive(true);
        user.setEnabled(true);
        user.setLoginByEmail(false);

        User editor = new User();
        editor.setEmail("editor@test.com");
        editor.setUserName("editor");
        editor.setPassword(passwordEncoder.encode("pass"));
        editor.setActive(true);
        editor.setEnabled(true);
        editor.setLoginByEmail(true);

        User moderator = new User();
        moderator.setEmail("moderator@test.com");
        moderator.setUserName("moderator");
        moderator.setPassword(passwordEncoder.encode("pass"));
        moderator.setActive(true);
        moderator.setEnabled(true);
        moderator.setLoginByEmail(true);

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(editor);
        userRepository.save(moderator);
    }
}
