package anak.om.mamat.latihan.modules.genres;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface GenreService {

    Page<DTO.respGenre> fetchGenres(Map<String, Object> filter);

}
