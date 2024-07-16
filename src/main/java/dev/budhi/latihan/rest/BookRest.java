package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.books.BookService;
import dev.budhi.latihan.modules.books.DTO;
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
@RequestMapping("/api/v1/book")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class BookRest {

    @Autowired
    BookService services;

    @Operation(summary = "Get all books", description = "Retrieve all books with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "List books not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.list<List<DTO.respBook>> fetching(@RequestParam Map<String, Object> filter) {

        Page<DTO.respBook> respBooks = services.getBooks(filter);

        return RestResponse.list.<List<DTO.respBook>>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .count_data(respBooks.getContent().size())
                .data(respBooks.getContent())
                .paging(RestResponse.restPagingResponse.builder()
                        .currentPage(respBooks.getNumber())
                        .totalPage(respBooks.getTotalPages())
                        .sizePage(respBooks.getSize())
                        .build())
                .build();

    }

    @Operation(summary = "Get a book by ID", description = "Retrieve detail book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.object<DTO.respDetailBook> getDetail(@PathVariable("id") Long id,
                                                             DTO.reqstDetailBook request) {

        request.setId(id);
        DTO.respDetailBook respDetailBook = services.getDetail(request);

        return RestResponse.object.<DTO.respDetailBook>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respDetailBook)
                .build();
    }

    @Operation(summary = "Create a Book", description = "Create a new Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The item was created successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found / Genre not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public ResponseEntity<RestResponse.object<DTO.respBook>> create(@RequestBody DTO.reqstCreateBook request) {

        DTO.respBook respBook = services.create(request);

        RestResponse.object<DTO.respBook> build = RestResponse.object.<DTO.respBook>builder()
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .data(respBook)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @Operation(summary = "Update a book", description = "Update an existing book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found / Author not found / Genre not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PutMapping(
            path = "/{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    public RestResponse.object<DTO.respBook> create(@PathVariable("id") Long id,
                                                    @RequestBody DTO.reqstUpdateBook request) {

        request.setId(id);
        DTO.respBook respBook = services.update(request);

        return RestResponse.object.<DTO.respBook>builder()
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .data(respBook)
                .build();

    }

    @Operation(summary = "Delete a book", description = "Delete an existing book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @DeleteMapping(
            path = "/{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:delete', 'management:delete')")
    public RestResponse.object<String> remove(@PathVariable("id") Long id,
                                              DTO.reqstDetailBook request) {

        request.setId(id);
        services.remove(request);

        return RestResponse.object.<String>builder()
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .data("")
                .build();

    }

}
