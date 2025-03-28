package epam.shared.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO implements Serializable {

    private String username;

    private String oldPassword;

    private String newPassword;
}
