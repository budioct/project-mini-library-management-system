package anak.om.mamat.latihan.modules.members;

import lombok.*;

import java.time.LocalDateTime;

public class DTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class respMember {
        private Long id;
        private String name;
        private String address;
        private String phone;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static respMember toRespMember(MemberEntity entity) {
        return respMember.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }

}
