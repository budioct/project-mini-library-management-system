package anak.om.mamat.latihan.modules.authors;

import anak.om.mamat.latihan.common.Models;
import anak.om.mamat.latihan.utilities.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    ValidationService validation;

    @Autowired
    AuthorRepository repository;

    @Transactional(readOnly = true)
    public Page<DTO.respAuthor> fetchAuthors(Map<String, Object> filter) {

        Models<AuthorEntity> models = new Models<>();
        Page<AuthorEntity> authorsPage = repository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respAuthor> respAuthors = authorsPage.getContent().stream().map(DTO::toRespAuthor).toList();

        if (respAuthors.size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "list Authors not found");
        }

        return new PageImpl<>(respAuthors, authorsPage.getPageable(), authorsPage.getTotalElements());
    }

}
