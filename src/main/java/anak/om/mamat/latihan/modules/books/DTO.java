package anak.om.mamat.latihan.modules.books;

import anak.om.mamat.latihan.modules.authors.AuthorEntity;
import anak.om.mamat.latihan.modules.genres.GenreEntity;
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
    public static class respAuthor {
        private String name;
    }

    @Getter
    @Setter
    @Builder
    public static class respGenre {
        private String name;
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

    public static respAuthor toRespAuthor(AuthorEntity entity) {
        return respAuthor.builder()
                .name(entity.getName())
                .build();
    }

    public static respGenre toRespGenre(GenreEntity entity){
        return respGenre.builder()
                .name(entity.getName())
                .build();
    }

}
