package anak.om.mamat.latihan.modules.genres;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface GenreService {

    Page<DTO.respGenre> fetchGenres(Map<String, Object> filter);

    DTO.respGenre detail(DTO.reqstDetailGenre request);

    DTO.respGenre create(DTO.reqstCreateGenre request);

    DTO.respGenre update(DTO.reqstUpdateGenre request);

}
