package edu.tlu.chat_host.entity;

import edu.tlu.chat_host.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
