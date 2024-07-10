package dev.budhi.latihan.modules.authors;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface AuthorService {

    Page<DTO.respAuthor> fetchAuthors(Map<String, Object> filter);

    DTO.respAuthor detail(DTO.reqstDetailAuthor request);

    DTO.respAuthor create(DTO.reqstCreateAuthor request);

    DTO.respAuthor update(DTO.reqstUpdateAuthor request);

    void remove(DTO.reqstDetailAuthor request);

}
