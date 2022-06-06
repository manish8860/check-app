package com.pass.check.security;

import com.pass.check.converter.RoleDtoConverter;
import com.pass.check.dto.RoleDto;
import com.pass.check.dto.TokenResponse;
import com.pass.check.dto.UserDto;
import com.pass.check.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtility implements Serializable {

    private final String ROLES_KEY = "roles";

    private final String secretKey;
    private final long validityInMilliseconds;

    @Autowired
    public JwtUtility(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}")long validityInMilliseconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public TokenResponse getToken(UserDto userDto) {
        Claims claims = Jwts.claims().setSubject(userDto.getUsername());
        claims.put(ROLES_KEY, userDto.getRoles().stream().map(RoleDto::getRole_name)
                .filter(Objects::nonNull).collect(Collectors.toSet()).stream().toList());

        Date now = new Date();
        Date expire = new Date(now.getTime() + validityInMilliseconds);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS384, secretKey)
                .compact();

        return returnTokenResponse(jwtToken);
    }

    private TokenResponse returnTokenResponse(String jwtToken) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtToken);
        return tokenResponse;
    }

    public boolean verifyToken(String token) {
        try{
            Jwts.parser().setSigningKey(secretKey).parse(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUserDto(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public List<RoleDto> getRolesDto(String token) {
        List<String> roleClaims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get(ROLES_KEY, List.class);

        return roleClaims.stream().map(roleClaim -> {
                Role role = new Role();
                role.setRoleName(roleClaim);
                return role;
            }).map(RoleDtoConverter::getRoleDto).collect(Collectors.toList());
    }
}
