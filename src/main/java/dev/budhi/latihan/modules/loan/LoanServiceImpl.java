package dev.budhi.latihan.modules.loan;

import dev.budhi.latihan.common.Models;
import dev.budhi.latihan.modules.books.BookEntity;
import dev.budhi.latihan.modules.books.BookRepository;
import dev.budhi.latihan.modules.members.MemberEntity;
import dev.budhi.latihan.modules.members.MemberRepository;
import dev.budhi.latihan.utilities.StringToDateConverter;
import dev.budhi.latihan.utilities.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoanServiceImpl implements LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);
    @Autowired
    private ValidationService validation;

    @Autowired
    private StringToDateConverter converter;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Transactional(readOnly = true)
    public Page<DTO.respLoan> fetchLoans(Map<String, Object> filter) {

        Models<LoanEntity> models = new Models<>();
        Page<LoanEntity> loansPage = loanRepository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respLoan> respLoans = loansPage.getContent().stream().map(DTO::toRespLoan).collect(Collectors.toList());

        if (respLoans.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List loans not found");
        }

        return new PageImpl<>(respLoans, loansPage.getPageable(), loansPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DTO.respLoan detail(DTO.reqstDetailLoan request) {
        validation.validate(request);

        LoanEntity loan = loanRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        return DTO.toRespLoan(loan);
    }

    @Transactional
    public DTO.respLoan create(DTO.reqstCreateLoan request) {
        validation.validate(request);

        MemberEntity member = memberRepository.findFirstById(request.getMember_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        BookEntity book = bookRepository.findFirstById(request.getBook_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        LoanEntity loan = new LoanEntity();
        loan.setDate_of_loan(converter.convert(request.getDate_of_loan()));
        loan.setDate_of_return(converter.convert(request.getDate_of_return()));
        loan.setMember(member);
        loan.setBook(book);

        loanRepository.save(loan);

        return DTO.toRespLoan(loan);
    }

    @Transactional
    public DTO.respLoan update(DTO.reqstUpdateLoan request) {
        validation.validate(request);

        LoanEntity loan = loanRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        MemberEntity member = memberRepository.findFirstById(request.getMember_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        BookEntity book = bookRepository.findFirstById(request.getBook_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        loan.setDate_of_loan(converter.convert(request.getDate_of_loan()));
        loan.setDate_of_return(converter.convert(request.getDate_of_return()));
        loan.setMember(member);
        loan.setBook(book);

        loanRepository.save(loan);

        return DTO.toRespLoan(loan);
    }

    @Transactional
    public void remove(DTO.reqstDetailLoan request) {
        validation.validate(request);

        LoanEntity loan = loanRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        loanRepository.delete(loan);
    }

}
