package anak.om.mamat.latihan.modules.members;

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
public class MemberServiceImpl implements MemberService {

    @Autowired
    ValidationService validation;

    @Autowired
    MemberRepository repository;

    @Transactional(readOnly = true)
    public Page<DTO.respMember> fetchMembers(Map<String, Object> filter) {

        Models<MemberEntity> models = new Models<>();
        Page<MemberEntity> membersPage = repository.findAll(models.where(filter), models.pageableSort(filter));
        List<DTO.respMember> respMembers = membersPage.getContent().stream().map(DTO::toRespMember).collect(Collectors.toList());

        if (respMembers.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "list Members not found");
        }

        return new PageImpl<>(respMembers, membersPage.getPageable(), membersPage.getTotalElements());
    }
}
