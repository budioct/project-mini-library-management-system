package dev.budhi.latihan.rest;

import dev.budhi.latihan.modules.members.DTO;
import dev.budhi.latihan.modules.members.MemberService;
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
@RequestMapping("/api/v1/member")
public class MemberRest {

    @Autowired
    MemberService services;

    @PostMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.list<List<DTO.respMember>> fetching(@RequestBody Map<String, Object> filter) {

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

    @PostMapping(
            path = "/fetch2",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.list<List<DTO.respMember>> fetching_ver2(@RequestParam Map<String, Object> filter) {

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

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse.object<DTO.respMember>> create(@RequestBody DTO.reqstMember request) {

        DTO.respMember respMember = services.create(request);

        RestResponse.object<DTO.respMember> build = RestResponse.object.<DTO.respMember>builder()
                .data(respMember)
                .status_code(Constants.CREATED)
                .message(Constants.CREATE_MESSAGE)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @PutMapping(
            path = "/{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
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

    @GetMapping(
            path = "/{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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

    @DeleteMapping(
            path = "{id}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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
