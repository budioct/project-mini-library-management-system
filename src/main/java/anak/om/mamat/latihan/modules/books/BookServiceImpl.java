package anak.om.mamat.latihan.modules.books;

import anak.om.mamat.latihan.common.Models;
import anak.om.mamat.latihan.utilities.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    ValidationService validation;

    @Autowired
    BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<DTO.respBook> getBooks(Map<String, Object> filter) {

        Models<BookEntity> models = new Models<>();
        Page<BookEntity> bookPage = bookRepository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respBook> respBooks = bookPage.getContent().stream().map(DTO::toRespBook).collect(Collectors.toList());

        if (respBooks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "list Authors not found");
        }

        return new PageImpl<>(respBooks, bookPage.getPageable(), bookPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DTO.respDetailBook getDetail(DTO.reqstDetailBook request) {
        validation.validate(request);

        BookEntity book = bookRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        return DTO.toRespDetailBook(book);
    }

}
