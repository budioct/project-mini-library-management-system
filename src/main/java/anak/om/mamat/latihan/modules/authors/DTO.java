package anak.om.mamat.latihan.modules.authors;

import lombok.*;

import java.time.LocalDateTime;

public class DTO {

    @Getter
    @Setter
    @Builder
    public static class respAuthor {
        private Long id;
        private String name;
        private String biography;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static respAuthor toRespAuthor(AuthorEntity entity) {
        return respAuthor.builder()
                .id(entity.getId())
                .name(entity.getName())
                .biography(entity.getBiography())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
