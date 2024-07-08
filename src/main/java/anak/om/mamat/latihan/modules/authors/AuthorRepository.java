package anak.om.mamat.latihan.modules.authors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long>, JpaSpecificationExecutor<AuthorEntity> {
}
