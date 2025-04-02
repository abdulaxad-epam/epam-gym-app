package epam.entity;


import epam.util.GeneratePassword;
import epam.util.GenerateUsername;
import epam.util.PasswordGeneratorListener;
import epam.util.UsernameGeneratorListener;
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
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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
    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @GeneratePassword
    @Column(nullable = false)


    private String password;

    @Column(nullable = false)
    private Boolean isActive;
}

