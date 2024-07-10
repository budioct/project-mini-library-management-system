package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.authors.AuthorService;
import dev.budhi.latihan.modules.authors.DTO;
import dev.budhi.latihan.rest.handler.RestResponse;
import dev.budhi.latihan.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorRest {

    @Autowired
    AuthorService services;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.list<List<DTO.respAuthor>> fetching(@RequestParam Map<String, Object> filter) {

        Page<DTO.respAuthor> respAuthors = services.fetchAuthors(filter);

        return RestResponse.list.<List<DTO.respAuthor>>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .count_data(respAuthors.getContent().size())
                .data(respAuthors.getContent())
                .paging(RestResponse.restPagingResponse.builder()
                        .currentPage(respAuthors.getNumber())
                        .totalPage(respAuthors.getTotalPages())
                        .sizePage(respAuthors.getSize())
                        .build())
                .build();

    }

    @GetMapping(
            path = "{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.respAuthor> detail(@PathVariable("id") Long id,
                                                      DTO.reqstDetailAuthor request) {
        request.setId(id);
        DTO.respAuthor respAuthor = services.detail(request);

        return RestResponse.object.<DTO.respAuthor>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respAuthor)
                .build();

    }

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse.object<DTO.respAuthor>> create(@RequestBody DTO.reqstCreateAuthor request) {

        DTO.respAuthor respAuthor = services.create(request);

        RestResponse.object<DTO.respAuthor> build = RestResponse.object.<DTO.respAuthor>builder()
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .data(respAuthor)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @PutMapping(
            path = "{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<DTO.respAuthor> update(@RequestBody DTO.reqstUpdateAuthor request,
                                                      @PathVariable("id") Long id) {

        request.setId(id);
        DTO.respAuthor respAuthor = services.update(request);

        return RestResponse.object.<DTO.respAuthor>builder()
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .data(respAuthor)
                .build();

    }

    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<String> remove(@PathVariable("id") Long id,
                                              DTO.reqstDetailAuthor request) {

        request.setId(id);
        services.remove(request);

        return RestResponse.object.<String>builder()
                .data("")
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }

}
