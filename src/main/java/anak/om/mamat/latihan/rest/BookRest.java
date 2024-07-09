package anak.om.mamat.latihan.rest;

import anak.om.mamat.latihan.modules.books.BookService;
import anak.om.mamat.latihan.modules.books.DTO;
import anak.om.mamat.latihan.rest.handler.RestResponse;
import anak.om.mamat.latihan.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/book")
public class BookRest {

    @Autowired
    BookService services;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.list<List<DTO.respBook>> fetching(@RequestParam Map<String, Object> filter){

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

}
