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

    private final ValidationService validation;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

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

    @Transactional
    public DTO.LoginResponse login(DTO.LoginRequest request) {
        validation.validate(request);

        UserEntity user = userRepository.findFirstByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Email"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return DTO.LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public DTO.LoginResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeder = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        DTO.LoginResponse data = null;

        if (authHeder == null || !authHeder.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeder.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            UserEntity user = this.userRepository.findFirstByEmail(userEmail)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                data = DTO.LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return data;
    }


    private void saveUserToken(UserEntity user, String jwtToken) {
        TokenEntity token = new TokenEntity();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity entity) {
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidtokenByUser(entity.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
    }


}

