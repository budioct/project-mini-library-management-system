package anak.om.mamat.latihan.rest;

import anak.om.mamat.latihan.modules.members.DTO;
import anak.om.mamat.latihan.modules.members.MemberService;
import anak.om.mamat.latihan.rest.handler.RestResponse;
import anak.om.mamat.latihan.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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


}
