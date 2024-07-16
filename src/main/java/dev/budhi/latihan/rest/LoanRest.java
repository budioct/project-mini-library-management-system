package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.loan.DTO;
import dev.budhi.latihan.modules.loan.LoanService;
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
@RequestMapping("/api/v1/loan")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class LoanRest {

    @Autowired
    LoanService services;

    @Operation(summary = "Get all loans", description = "Retrieve all loans with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "List loans not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.list<List<DTO.respLoan>> fetching(@RequestParam Map<String, Object> filter) {

        Page<DTO.respLoan> respLoans = services.fetchLoans(filter);

        return RestResponse.list.<List<DTO.respLoan>>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .count_data(respLoans.getContent().size())
                .data(respLoans.getContent())
                .paging(RestResponse.restPagingResponse.builder()
                        .currentPage(respLoans.getNumber())
                        .totalPage(respLoans.getTotalPages())
                        .sizePage(respLoans.getSize())
                        .build())
                .build();

    }

    @Operation(summary = "Get a loan by ID", description = "Retrieve detail loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.object<DTO.respLoan> detail(@PathVariable("id") Long id,
                                                    DTO.reqstDetailLoan request) {

        request.setId(id);
        DTO.respLoan respLoan = services.detail(request);

        return RestResponse.object.<DTO.respLoan>builder()
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respLoan)
                .build();

    }

    @Operation(summary = "Create a Loan", description = "Create a new Loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The item was created successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found / Book not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public ResponseEntity<RestResponse.object<DTO.respLoan>> create(@RequestBody DTO.reqstCreateLoan request) {

        DTO.respLoan respLoan = services.create(request);

        RestResponse.object<DTO.respLoan> build = RestResponse.object.<DTO.respLoan>builder()
                .status_code(Constants.CREATED)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respLoan)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @Operation(summary = "Update a loan", description = "Update an existing loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was updated successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found / Member not found / Book not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PutMapping(
            path = "/{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    public RestResponse.object<DTO.respLoan> update(@PathVariable("id") Long id,
                                                    @RequestBody DTO.reqstUpdateLoan request) {

        request.setId(id);
        DTO.respLoan respLoan = services.update(request);

        return RestResponse.object.<DTO.respLoan>builder()
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .data(respLoan)
                .build();

    }

    @Operation(summary = "Delete a loan", description = "Delete an existing loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:delete', 'management:delete')")
    public RestResponse.object<String> remove(@PathVariable("id") Long id,
                                              DTO.reqstDetailLoan request) {

        request.setId(id);
        services.remove(request);

        return RestResponse.object.<String>builder()
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .data("")
                .build();

    }


}
