package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.authors.AuthorService;
import dev.budhi.latihan.modules.authors.DTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorRest {

    @Autowired
    AuthorService services;

    @Operation(summary = "Get all authors", description = "Retrieve all authors with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "List of authors not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
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

    @Operation(summary = "Get a author by ID", description = "Retrieve detail author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
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

    @Operation(summary = "Create a Author", description = "Create a new Author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The item was created successfully"),
    })
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

    @Operation(summary = "Update a author", description = "Update an existing author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
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

    @Operation(summary = "Delete a author", description = "Delete an existing author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
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
