package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.genres.DTO;
import dev.budhi.latihan.modules.genres.GenreService;
import dev.budhi.latihan.rest.handler.RestResponse;
import dev.budhi.latihan.utilities.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/genre")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class GenreRest {

    @Autowired
    GenreService services;

    @Operation(summary = "Get all genres", description = "Retrieve all genres with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "List genres not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
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

    @Operation(summary = "Get a genre by ID", description = "Retrieve detail genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
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

    @Operation(summary = "Create a Genre", description = "Create a new Genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The item was created successfully"),
    })
    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public ResponseEntity<RestResponse.object<DTO.respGenre>> create(@RequestBody DTO.reqstCreateGenre request) {

        DTO.respGenre respGenre = services.create(request);

        RestResponse.object<DTO.respGenre> build = RestResponse.object.<DTO.respGenre>builder()
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .data(respGenre)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }


    @Operation(summary = "Update a genre", description = "Update an existing genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was updated successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PutMapping(
            path = "{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
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

    @Operation(summary = "Delete a genre", description = "Delete an existing genre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:delete', 'management:delete')")
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
