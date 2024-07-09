package anak.om.mamat.latihan.modules.loan;

import anak.om.mamat.latihan.common.Models;
import anak.om.mamat.latihan.modules.books.BookRepository;
import anak.om.mamat.latihan.modules.members.MemberRepository;
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
public class LoanServiceImpl implements LoanService {

    @Autowired
    private ValidationService validation;

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

    @Transactional
    public DTO.respLoan detail(DTO.reqstDetailLoan request) {
        validation.validate(request);

        LoanEntity loan = loanRepository.findFirstById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan not found"));

        return DTO.toRespLoan(loan);
    }
}
