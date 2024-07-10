package dev.budhi.latihan.modules.authors;

import dev.budhi.latihan.common.Models;
import dev.budhi.latihan.utilities.ValidationService;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Authors not found");
        }

        return new PageImpl<>(respAuthors, authorsPage.getPageable(), authorsPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DTO.respAuthor detail(DTO.reqstDetailAuthor request) {
        validation.validate(request);

        AuthorEntity author = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        return DTO.toRespAuthor(author);
    }

    @Transactional
    public DTO.respAuthor create(DTO.reqstCreateAuthor request) {
        validation.validate(request);

        AuthorEntity author = new AuthorEntity();
        author.setName(request.getName());
        author.setBiography(request.getBiography());

        repository.save(author);

        return DTO.toRespAuthor(author);
    }

    @Transactional
    public DTO.respAuthor update(DTO.reqstUpdateAuthor request) {
        validation.validate(request);

        AuthorEntity author = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        author.setName(request.getName());
        author.setBiography(request.getBiography());

        repository.save(author);

        return DTO.toRespAuthor(author);
    }

    @Transactional
    public void remove(DTO.reqstDetailAuthor request) {
        validation.validate(request);

        AuthorEntity author = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        repository.delete(author);
    }

}
