package anak.om.mamat.latihan.modules.loan;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface LoanService {

    Page<DTO.respLoan> fetchLoans(Map<String, Object> filter);

    DTO.respLoan detail(DTO.reqstDetailLoan request);

    DTO.respLoan create(DTO.reqstCreateLoan request);

}
