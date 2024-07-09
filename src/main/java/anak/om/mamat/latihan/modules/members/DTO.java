package anak.om.mamat.latihan.modules.members;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class reqstDetailMember {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class reqstMember {
        @NotBlank
        @NotNull
        private String name;
        @NotBlank
        @NotNull
        private String address;
        @NotBlank
        @NotNull
        @Size(min = 9, max = 12)
        private String phone;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class reqstUpdateMember {
        @JsonIgnore
        private Long id;
        @NotBlank
        private String name;
        @NotBlank
        private String address;
        @NotBlank
        @Size(min = 9, max = 12)
        private String phone;
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
