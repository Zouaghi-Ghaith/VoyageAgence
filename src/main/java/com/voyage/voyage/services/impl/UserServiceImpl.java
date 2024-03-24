package com.voyage.voyage.services.impl;


import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import com.voyage.voyage.config.JwtUtils;
import com.voyage.voyage.dto.AuthenticationRequest;
import com.voyage.voyage.dto.AuthenticationResponse;
import com.voyage.voyage.dto.UserDto;
import com.voyage.voyage.models.Role;
import com.voyage.voyage.models.Usera;
import com.voyage.voyage.repositories.RoleRepository;
import com.voyage.voyage.repositories.UserRepository;
import com.voyage.voyage.services.UserService;
import com.voyage.voyage.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final ObjectsValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;

    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        Usera user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user).getId();
    }



    @Override
    @Transactional
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check before delete
        repository.deleteById(id);
    }



    @Override
    public Integer invalidateAccount(Integer id) {
        Usera user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        user.setActive(false);
        repository.save(user);
        return user.getId();
    }


    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);

        Usera user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assuming findOrCreateRoles() returns a Set<Role> for the selected roles
        Set<Role> userRoles = findOrCreateRoles(dto.getRoleNames());
        user.setRoles(userRoles);

        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser, claims);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }




    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final Usera user = repository.findByEmail(request.getEmail()).get();

        // Create claims with user information, including roles
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        final String token = jwtUtils.generateToken(user, claims);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }




    private Set<Role> findOrCreateRoles(List<String> roleNames) {
        Set<Role> roles = new HashSet<>();

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElse(null);

            if (role == null) {
                role = roleRepository.save(
                        Role.builder()
                                .name(roleName)
                                .build()
                );
            }

            roles.add(role);
        }

        return roles;
    }

}

