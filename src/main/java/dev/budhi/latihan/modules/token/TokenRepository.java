package dev.budhi.latihan.modules.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);

    @Query(value = """
            select t from TokenEntity t inner join UserEntity u\s
            on t.user.id = u.id\s
            where u.id = :userId and (t.expired = false or t.revoked = false )\s
            """)
    List<TokenEntity> findAllValidtokenByUser(Long userId);


}
