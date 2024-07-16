package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.members.DTO;
import dev.budhi.latihan.modules.members.MemberService;
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
@RequestMapping("/api/v1/member")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class MemberRest {

    @Autowired
    MemberService services;

    @Operation(summary = "Get all members", description = "Retrieve all members with optional pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "List members not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.list<List<DTO.respMember>> fetching(@RequestParam Map<String, Object> filter) {

        Page<DTO.respMember> respMembers = services.fetchMembers(filter);

        return RestResponse.list.<List<DTO.respMember>>builder()
                .data(respMembers.getContent())
                .count_data(respMembers.getContent().size())
                .paging(RestResponse.restPagingResponse.builder()
                        .currentPage(respMembers.getNumber())
                        .totalPage(respMembers.getTotalPages())
                        .sizePage(respMembers.getSize())
                        .build())
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @Operation(summary = "Create a Member", description = "Create a new Member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The item was created successfully"),
            @ApiResponse(responseCode = "400", description = "Phone number already in use", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public ResponseEntity<RestResponse.object<DTO.respMember>> create(@RequestBody DTO.reqstMember request) {

        DTO.respMember respMember = services.create(request);

        RestResponse.object<DTO.respMember> build = RestResponse.object.<DTO.respMember>builder()
                .data(respMember)
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @Operation(summary = "Update a member", description = "Update an existing member by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was updated successfully"),
            @ApiResponse(responseCode = "400", description = "Phone number already in use", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @PutMapping(
            path = "/{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    public RestResponse.object<DTO.respMember> update(@RequestBody DTO.reqstUpdateMember request,
                                                      @PathVariable Long id) {

        request.setId(id);
        DTO.respMember respMember = services.update(request);

        return RestResponse.object.<DTO.respMember>builder()
                .data(respMember)
                .status_code(Constants.OK)
                .message(Constants.UPDATE_MESSAGE)
                .build();

    }

    @Operation(summary = "Get a member by ID", description = "Retrieve detail member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item exist"),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @GetMapping(
            path = "/{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public RestResponse.object<DTO.respMember> detail(@PathVariable("id") Long id,
                                                      DTO.reqstDetailMember request) {

        request.setId(id);
        DTO.respMember respMember = services.detail(request);

        return RestResponse.object.<DTO.respMember>builder()
                .data(respMember)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();

    }

    @Operation(summary = "Delete a member", description = "Delete an existing member by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item was deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content(schema = @Schema(implementation = RestResponse.restError.class))),
    })
    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyAuthority('admin:delete', 'management:delete')")
    public RestResponse.object<String> remove(@PathVariable("id") Long id,
                                              DTO.reqstDetailMember request) {

        request.setId(id);
        services.remove(request);

        return RestResponse.object.<String>builder()
                .data("")
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();

    }

}
