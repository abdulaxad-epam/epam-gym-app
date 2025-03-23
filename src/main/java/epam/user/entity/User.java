package epam.user.entity;


import epam.shared.util.GeneratePassword;
import epam.shared.util.GenerateUsername;
import epam.shared.util.PasswordGeneratorListener;
import epam.shared.util.UsernameGeneratorListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@EntityListeners(value = {PasswordGeneratorListener.class, UsernameGeneratorListener.class})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @GenerateUsername
    @Column(nullable = false, unique = true)
    private String username;

    @GeneratePassword
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isActive;
}

