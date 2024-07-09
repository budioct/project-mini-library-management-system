package anak.om.mamat.latihan.modules.books;

import anak.om.mamat.latihan.modules.authors.AuthorEntity;
import anak.om.mamat.latihan.modules.genres.GenreEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class DTO {

    @Getter
    @Setter
    @Builder
    public static class respBook {
        private Long id;
        private String title;
        private respAuthor author;
        private respGenre genre;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class respDetailBook {
        private Long id;
        private String title;
        private respDetailAuthor author;
        private respDetailGenre genre;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstDetailBook {
        @JsonIgnore
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @Builder
    public static class respAuthor {
        private String name;
    }

    @Getter
    @Setter
    @Builder
    public static class respDetailAuthor {
        private String name;
        private String biography;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class respGenre {
        private String name;
    }

    @Getter
    @Setter
    @Builder
    public static class respDetailGenre {
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static respBook toRespBook(BookEntity entity) {
        return respBook.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(toRespAuthor(entity.getAuthor()))
                .genre(toRespGenre(entity.getGenre()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static respDetailBook toRespDetailBook(BookEntity entity) {
        return respDetailBook.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(toRespDetailAuthor(entity.getAuthor()))
                .genre(toRespDetailGenre(entity.getGenre()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static respAuthor toRespAuthor(AuthorEntity entity) {
        return respAuthor.builder()
                .name(entity.getName())
                .build();
    }

    public static respDetailAuthor toRespDetailAuthor(AuthorEntity entity) {
        return respDetailAuthor.builder()
                .name(entity.getName())
                .biography(entity.getBiography())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static respGenre toRespGenre(GenreEntity entity) {
        return respGenre.builder()
                .name(entity.getName())
                .build();
    }

    public static respDetailGenre toRespDetailGenre(GenreEntity entity) {
        return respDetailGenre.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
