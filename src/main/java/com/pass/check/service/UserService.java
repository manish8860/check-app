package com.pass.check.service;

import com.pass.check.converter.UserDtoConverter;
import com.pass.check.dao.RoleDao;
import com.pass.check.dao.UserDao;
import com.pass.check.dto.TokenResponse;
import com.pass.check.dto.UserDto;
import com.pass.check.entity.Role;
import com.pass.check.entity.User;
import com.pass.check.security.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username).orElseThrow(() ->
            new RuntimeException("Wrong username and password")
        );

        return withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public void addUser(final UserDto userDto) {
        Optional<User> existingUser = userDao.findByUsername(userDto.getUsername());
        if(existingUser.isPresent()){ throw new RuntimeException("User already exits!"); }
        User user = UserDtoConverter.getUser(userDto);
        List<Role> roleList = getUserRoles(userDto);
        user.setRoles(roleList);
        userDao.save(user);
    }

    private List<Role> getUserRoles(UserDto userDto) {
        return userDto.getRoles().stream().map(roleDto ->
            roleDao.findByRoleName(roleDto.getRole_name()).orElseThrow(() ->
                    new RuntimeException("No such role can be assigned."))
        ).filter(Objects::nonNull).collect(Collectors.toSet()).stream().toList();
    }

    public TokenResponse checkUser(final UserDto userDto) {
        Optional<User> existingUser = userDao.findByUsername(userDto.getUsername());
        if(existingUser.isEmpty()){ throw new RuntimeException("No such User exits!"); }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(), userDto.getPassword()));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return jwtUtility.getToken(UserDtoConverter.getUserDto(existingUser.get()));
    }

    public UserDto getUserFromToken(String token) {
        UserDto userDto = new UserDto();
        userDto.setUsername(jwtUtility.getUserDto(token));
        userDto.setRoles(jwtUtility.getRolesDto(token));
        return userDto;
    }
}
