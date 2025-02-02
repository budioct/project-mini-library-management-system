package dev.budhi.latihan.rest;

import dev.budhi.latihan.config.security.LogoutService;
import dev.budhi.latihan.modules.users.DTO;
import dev.budhi.latihan.modules.users.UserService;
import dev.budhi.latihan.rest.handler.RestResponse;
import dev.budhi.latihan.utilities.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserRest {

    private final UserService services;
    private final LogoutService logoutService;

    @PostMapping(
            path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse.object<DTO.RegisterResponse>> register(@RequestBody DTO.RegisterRequest request) {

        DTO.RegisterResponse loginResponse = services.register(request);

        RestResponse.object<DTO.RegisterResponse> build = RestResponse.object.<DTO.RegisterResponse>builder()
                .status_code(Constants.CREATED)
                .message(Constants.REGISTER_MESSAGE)
                .data(loginResponse)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.LoginResponse> login(@RequestBody DTO.LoginRequest request) {

        DTO.LoginResponse loginResponse = services.login(request);

        return RestResponse.object.<DTO.LoginResponse>builder()
                .status_code(Constants.OK)
                .message(Constants.AUTH_LOGIN_MESSAGE)
                .data(loginResponse)
                .build();

    }

    @PostMapping(
            path = "/refresh-token",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.LoginResponse> refreshToken(HttpServletRequest request,
                                                               HttpServletResponse response) throws IOException {

        DTO.LoginResponse loginResponse = services.refreshToken(request, response);

        return RestResponse.object.<DTO.LoginResponse>builder()
                .status_code(Constants.OK)
                .message(Constants.AUTH_REFRESH_TOKEN_MESSAGE)
                .data(loginResponse)
                .build();

    }

    @PostMapping(
            path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<String> logout(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication authentication) {

        logoutService.logout(request, response, authentication);

        return RestResponse.object.<String>builder()
                .status_code(Constants.OK)
                .message(Constants.AUTH_LOGOUT_MESSAGE)
                .data("")
                .build();

    }

    @PostMapping(
            path = "/change-password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<String> changePassword(@RequestBody DTO.ChangePasswordRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails) {

        services.changePassword(request, userDetails);

        return RestResponse.object.<String>builder()
                .status_code(Constants.OK)
                .message(Constants.AUTH_CHANGE_PASSWORD_MESSAGE)
                .data("")
                .build();

    }



}
