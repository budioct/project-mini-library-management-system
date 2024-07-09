package anak.om.mamat.latihan.modules.books;

import anak.om.mamat.latihan.common.Models;
import anak.om.mamat.latihan.modules.authors.AuthorEntity;
import anak.om.mamat.latihan.modules.authors.AuthorRepository;
import anak.om.mamat.latihan.modules.genres.GenreEntity;
import anak.om.mamat.latihan.modules.genres.GenreRepository;
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

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public Page<DTO.respBook> getBooks(Map<String, Object> filter) {

        Models<BookEntity> models = new Models<>();
        Page<BookEntity> bookPage = bookRepository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respBook> respBooks = bookPage.getContent().stream().map(DTO::toRespBook).collect(Collectors.toList());

        if (respBooks.isEmpty()) {
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

    @Transactional
    public DTO.respBook create(DTO.reqstCreateBook request) {
        validation.validate(request);

        AuthorEntity author = authorRepository.findFirstById(request.getAuthor_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        GenreEntity genre = genreRepository.findFirstById(request.getGenre_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        BookEntity book = new BookEntity();
        book.setTitle(request.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);

        return DTO.toRespBook(book);
    }

}
