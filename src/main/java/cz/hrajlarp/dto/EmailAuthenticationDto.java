package cz.hrajlarp.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jbalhar on 5/30/2014.
 */
public class EmailAuthenticationDto {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }
}
