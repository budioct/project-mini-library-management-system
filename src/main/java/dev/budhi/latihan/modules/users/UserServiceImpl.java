package dev.budhi.latihan.modules.users;

import dev.budhi.latihan.config.security.JwtService;
import dev.budhi.latihan.modules.token.TokenEntity;
import dev.budhi.latihan.modules.token.TokenRepository;
import dev.budhi.latihan.modules.token.TokenType;
import dev.budhi.latihan.utilities.BCrypt;
import dev.budhi.latihan.utilities.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final ValidationService validation;

    @Transactional
    public DTO.RegisterResponse register(DTO.RegisterRequest request) {
        validation.validate(request);

        if (userRepository.findFirstByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        UserEntity saveUser = userRepository.save(user);

        return DTO.RegisterResponse
                .builder()
                .email(saveUser.getEmail())
                .role(saveUser.getRole())
                .build();
    }

}

