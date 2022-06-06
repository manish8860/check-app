package com.pass.check.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Table(name = "pass_data",
    uniqueConstraints = @UniqueConstraint(columnNames = "code")
)
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{3,20}", message = "Name must contain only alphabets, no space and length within 3 to 20.")
    String name;

    @NotNull
    @Column(name = "code")
    @Pattern(regexp = "^[a-zA-z0-9]{10}", message = "Code is alphanumeric and length is 10.")
    String code;

}
