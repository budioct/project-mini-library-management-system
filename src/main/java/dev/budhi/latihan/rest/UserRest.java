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


}
