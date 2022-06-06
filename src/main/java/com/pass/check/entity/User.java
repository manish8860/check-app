package com.pass.check.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pass.check.converter.PasswordEncoderConvertor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "SECURITY_USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "User name should only have alphabet, length between 5 to 20.")
    private String username;

    @NotNull
    @JsonIgnore
    @Convert(converter = PasswordEncoderConvertor.class)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;
}
