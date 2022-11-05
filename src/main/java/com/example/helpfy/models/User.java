package com.example.helpfy.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String avatarLink;
}
