package epam.shared.security.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Old password is required")
    @Size(min = 10, max = 10, message = "Password length must be exactly 10 characters")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 10, max = 10, message = "Password length must be exactly 10 characters")
    private String newPassword;
}
