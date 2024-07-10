package anak.om.mamat.latihan.modules.loan;

import anak.om.mamat.latihan.modules.authors.AuthorEntity;
import anak.om.mamat.latihan.modules.books.BookEntity;
import anak.om.mamat.latihan.modules.genres.GenreEntity;
import anak.om.mamat.latihan.modules.members.MemberEntity;
import anak.om.mamat.latihan.utilities.StringToDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DTO {

    @Getter
    @Setter
    @Builder
    public static class respLoan {
        private Long id;
        private String date_of_loan;
        private String date_of_return;
        private respMember member;
        private respBook book;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

    @Getter
    @Setter
    @Builder
    public static class reqstDetailLoan {
        @JsonIgnore
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstCreateLoan {
        @NotBlank
        private String date_of_loan;
        @NotBlank
        private String date_of_return;
        @NotNull
        private Long member_id;
        @NotNull
        private Long book_id;
    }

    @Getter
    @Setter
    @Builder
    public static class reqstUpdateLoan {
        @JsonIgnore
        @NotNull
        private Long id;
        @NotBlank
        private String date_of_loan;
        @NotBlank
        private String date_of_return;
        @NotNull
        private Long member_id;
        @NotNull
        private Long book_id;
    }


    @Getter
    @Setter
    @Builder
    public static class respMember {
        private String name;
        private String address;
        private String phone;
    }

    @Getter
    @Setter
    @Builder
    public static class respBook {
        private String title;
        private respAuthor author;
        private respGenre genre;
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

    public static respLoan toRespLoan(LoanEntity entity) {
        return respLoan.builder()
                .id(entity.getId())
                .date_of_loan(StringToDateConverter.convert(entity.getDate_of_loan()))
                .date_of_return(StringToDateConverter.convert(entity.getDate_of_return()))
                .book(toRespBook(entity.getBook()))
                .member(toRespMember(entity.getMember()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static respMember toRespMember(MemberEntity entity) {
        return respMember.builder()
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();

    }

    public static respBook toRespBook(BookEntity entity) {
        return respBook.builder()
                .title(entity.getTitle())
                .author(toRespAuthor(entity.getAuthor()))
                .genre(toRespGenre(entity.getGenre()))
                .build();
    }

    public static respAuthor toRespAuthor(AuthorEntity entity) {
        return respAuthor.builder()
                .name(entity.getName())
                .build();
    }

    public static respGenre toRespGenre(GenreEntity entity) {
        return respGenre.builder()
                .name(entity.getName())
                .build();
    }

}
