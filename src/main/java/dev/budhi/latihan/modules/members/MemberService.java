package dev.budhi.latihan.modules.members;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface MemberService {

    Page<DTO.respMember> fetchMembers(Map<String, Object> filter);

    DTO.respMember create(DTO.reqstMember request);

    DTO.respMember update(DTO.reqstUpdateMember request);

    DTO.respMember detail(DTO.reqstDetailMember request);

    void remove(DTO.reqstDetailMember request);

}
