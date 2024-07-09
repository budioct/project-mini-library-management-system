package anak.om.mamat.latihan.rest;

import anak.om.mamat.latihan.modules.genres.DTO;
import anak.om.mamat.latihan.modules.genres.GenreService;
import anak.om.mamat.latihan.rest.handler.RestResponse;
import anak.om.mamat.latihan.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreRest {

    @Autowired
    GenreService services;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.list<List<DTO.respGenre>> fetching(@RequestParam Map<String, Object> filter) {

        Page<DTO.respGenre> respGenres = services.fetchGenres(filter);

        return RestResponse.list.<List<DTO.respGenre>>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .count_data(respGenres.getContent().size())
                .data(respGenres.getContent())
                .paging(RestResponse.restPagingResponse.builder()
                        .currentPage(respGenres.getNumber())
                        .totalPage(respGenres.getTotalPages())
                        .sizePage(respGenres.getSize())
                        .build())
                .build();

    }

    @GetMapping(
            path = "{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.respGenre> detail(@PathVariable("id") Long id,
                                                     DTO.reqstDetailGenre request) {

        request.setId(id);
        DTO.respGenre respGenre = services.detail(request);

        return RestResponse.object.<DTO.respGenre>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respGenre)
                .build();

    }

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse.object<DTO.respGenre>> create(@RequestBody DTO.reqstCreateGenre request) {

        DTO.respGenre respGenre = services.create(request);

        RestResponse.object<DTO.respGenre> build = RestResponse.object.<DTO.respGenre>builder()
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .data(respGenre)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @PutMapping(
            path = "{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.respGenre> update(@PathVariable("id") Long id,
                                                     @RequestBody DTO.reqstUpdateGenre request) {

        request.setId(id);
        DTO.respGenre respGenre = services.update(request);

        return RestResponse.object.<DTO.respGenre>builder()
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .data(respGenre)
                .build();

    }

    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<String> remove(@PathVariable("id") Long id,
                                              DTO.reqstDetailGenre request) {

        request.setId(id);
        services.remove(request);

        return RestResponse.object.<String>builder()
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .data("")
                .build();

    }

}
