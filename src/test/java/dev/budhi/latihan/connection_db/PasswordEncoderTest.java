package dev.budhi.latihan.connection_db;

import dev.budhi.latihan.utilities.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testEncodeDecodeBCyrpt(){

        String password = "rahasia";
        String hashpw1 = passwordEncoder.encode(password);

        boolean checkpw = BCrypt.checkpw("rahasia", hashpw1);
        System.out.println("password encode " + hashpw1);
        System.out.println("status password " + checkpw);

    }

    @Test
    void testPasswordEncoder(){

        String password = "rahasia";
        String hashpw1 = passwordEncoder.encode(password);

        boolean checkpw = passwordEncoder.matches("rahasia", hashpw1);

        System.out.println("password encode " + hashpw1);
        System.out.println("status password " + checkpw);

    }


}
