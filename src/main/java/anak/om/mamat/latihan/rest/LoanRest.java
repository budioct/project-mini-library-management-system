package anak.om.mamat.latihan.rest;

import anak.om.mamat.latihan.modules.loan.DTO;
import anak.om.mamat.latihan.modules.loan.LoanService;
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
@RequestMapping("/api/v1/loan")
public class LoanRest {

    @Autowired
    LoanService services;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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

    @GetMapping(
            path = "/{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse.object<DTO.respLoan>> create(@RequestBody DTO.reqstCreateLoan request) {

        DTO.respLoan respLoan = services.create(request);

        RestResponse.object<DTO.respLoan> build = RestResponse.object.<DTO.respLoan>builder()
                .status_code(Constants.CREATED)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .data(respLoan)
                .build();

        return new ResponseEntity<>(build, HttpStatus.CREATED);

    }

    @PutMapping(
            path = "/{id}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
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


}
