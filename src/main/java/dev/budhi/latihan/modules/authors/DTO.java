package dev.budhi.latihan.modules.authors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Getter
    @Setter
    @Builder
    public static class reqstDetailAuthor {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstCreateAuthor {
        @NotBlank
        @Size(min = 2, max = 100)
        private String name;
        @NotBlank
        private String biography;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstUpdateAuthor {
        @JsonIgnore
        private Long id;
        @NotBlank
        @Size(min = 2, max = 100)
        private String name;
        @NotBlank
        private String biography;
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
