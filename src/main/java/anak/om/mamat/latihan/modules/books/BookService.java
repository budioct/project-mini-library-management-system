package anak.om.mamat.latihan.modules.books;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface BookService {

    Page<DTO.respBook> getBooks(Map<String, Object> filter);

    DTO.respDetailBook getDetail(DTO.reqstDetailBook request);

    DTO.respBook create(DTO.reqstCreateBook request);

}
