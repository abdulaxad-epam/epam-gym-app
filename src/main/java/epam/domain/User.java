package epam.domain;


import epam.util.PasswordGenerator;
import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID userId;

    private String firstname;

    private String lastname;

    private String username;

    @Builder.Default
    private String password = PasswordGenerator.generatePassword(10);

    private Boolean isActive;

}

