package epam.domain;


import epam.util.PasswordGenerator;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Builder.Default
    private UUID userId = UUID.randomUUID();

    private String firstname;


    private String lastname;

    private String username;

    @Builder.Default
    private String password = PasswordGenerator.generatePassword(10);

    private Boolean isActive;

}

