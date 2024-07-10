package dev.budhi.latihan.modules.genres;

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
public class GenreServiceImpl implements GenreService {

    @Autowired
    ValidationService validation;

    @Autowired
    GenreRepository repository;

    @Transactional(readOnly = true)
    public Page<DTO.respGenre> fetchGenres(Map<String, Object> filter) {

        Models<GenreEntity> models = new Models<>();
        Page<GenreEntity> genresPage = repository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respGenre> respGenres = genresPage.getContent().stream().map(DTO::toRespGenre).toList();

        if (respGenres.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "list Genres not found");
        }

        return new PageImpl<>(respGenres, genresPage.getPageable(), genresPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DTO.respGenre detail(DTO.reqstDetailGenre request) {
        validation.validate(request);

        GenreEntity genre = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        return DTO.toRespGenre(genre);
    }

    @Transactional
    public DTO.respGenre create(DTO.reqstCreateGenre request) {
        validation.validate(request);

        GenreEntity genre = new GenreEntity();
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());

        repository.save(genre);

        return DTO.toRespGenre(genre);
    }

    @Transactional
    public DTO.respGenre update(DTO.reqstUpdateGenre request) {
        validation.validate(request);

        GenreEntity genre = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        genre.setName(request.getName());
        genre.setDescription(request.getDescription());

        repository.save(genre);

        return DTO.toRespGenre(genre);
    }

    @Transactional
    public void remove(DTO.reqstDetailGenre request) {
        validation.validate(request);

        GenreEntity genre = repository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        repository.delete(genre);

    }

}
