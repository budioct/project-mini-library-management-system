package anak.om.mamat.latihan.modules.loan;

import anak.om.mamat.latihan.common.Models;
import anak.om.mamat.latihan.modules.books.BookEntity;
import anak.om.mamat.latihan.modules.books.BookRepository;
import anak.om.mamat.latihan.modules.members.MemberEntity;
import anak.om.mamat.latihan.modules.members.MemberRepository;
import anak.om.mamat.latihan.utilities.StringToDateConverter;
import anak.om.mamat.latihan.utilities.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No loan found");
        }

        return new PageImpl<>(respLoans, loansPage.getPageable(), loansPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DTO.respLoan detail(DTO.reqstDetailLoan request) {
        validation.validate(request);

        LoanEntity loan = loanRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan not found"));

        return DTO.toRespLoan(loan);
    }

    @Transactional
    public DTO.respLoan create(DTO.reqstCreateLoan request) {
        validation.validate(request);

        MemberEntity member = memberRepository.findFirstById(request.getMember_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member not found"));

        BookEntity book = bookRepository.findFirstById(request.getBook_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan not found"));

        MemberEntity member = memberRepository.findFirstById(request.getMember_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member not found"));

        BookEntity book = bookRepository.findFirstById(request.getBook_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));

        loan.setDate_of_loan(converter.convert(request.getDate_of_loan()));
        loan.setDate_of_return(converter.convert(request.getDate_of_return()));
        loan.setMember(member);
        loan.setBook(book);

        loanRepository.save(loan);

        return DTO.toRespLoan(loan);
    }

}
