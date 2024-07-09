package anak.om.mamat.latihan.modules.genres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class DTO {

    @Getter
    @Setter
    @Builder
    public static class respGenre {
        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstDetailGenre {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstCreateGenre {
        @NotBlank
        private String name;
        @NotBlank
        private String description;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstUpdateGenre {
        @JsonIgnore
        private Long id;
        @NotBlank
        private String name;
        @NotBlank
        private String description;
    }

    public static respGenre toRespGenre(GenreEntity entity) {
        return respGenre.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
