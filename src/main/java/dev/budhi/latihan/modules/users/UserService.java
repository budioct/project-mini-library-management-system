package dev.budhi.latihan.modules.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface UserService {

    DTO.RegisterResponse register(DTO.RegisterRequest request);

}
