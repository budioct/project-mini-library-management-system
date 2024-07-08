package anak.om.mamat.latihan.modules.members;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface MemberService {

    Page<DTO.respMember> fetchMembers(Map<String, Object> filter);

}
