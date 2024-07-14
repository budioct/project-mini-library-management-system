package dev.budhi.latihan.modules.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.budhi.latihan.utilities.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DTO {

    @Getter
    @Setter
    @Builder
    public static class RegisterResponse {
        private String email;
        //private String password;
        private Role role;
    }

    @Getter
    @Setter
    @Builder
    public static class RegisterRequest {
        @NotBlank
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
        private String email;
        @NotBlank
        @Size(min = 8, max = 255)
        private String password;
        @NotBlank
        @ValidEnum(enumClass = Role.class, message = "Invalid role")
        private String role;
    }

    @Getter
    @Setter
    @Builder
    public static class LoginResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
    }

    @Getter
    @Setter
    @Builder
    public static class LoginRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    @Builder
    public static class ChangePasswordRequest {
        @NotBlank
        @Size(min = 8, max = 255)
        private String currentPassword;
        @NotBlank
        @Size(min = 8, max = 255)
        private String newPassword;
        @NotBlank
        @Size(min = 8, max = 255)
        private String confirmationPassword;
    }



}
