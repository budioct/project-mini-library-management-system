package dev.budhi.latihan.modules.members;

import dev.budhi.latihan.modules.authors.AuthorEntity;
import dev.budhi.latihan.modules.books.BookEntity;
import dev.budhi.latihan.modules.genres.GenreEntity;
import dev.budhi.latihan.modules.loan.LoanEntity;
import dev.budhi.latihan.utilities.StringToDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        private List<respLoan> loans;
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

    @Getter
    @Setter
    @Builder
    public static class respLoan {
        private Long id;
        private String date_of_loan;
        private String date_of_return;
        private respBook book;

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


    public static respMember toRespMember(MemberEntity entity) {
        return respMember.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .loans(entity.getLoans().stream().map(DTO::toRespLoan).collect(Collectors.toList()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }

    public static respLoan toRespLoan(LoanEntity entity) {
        return respLoan.builder()
                .id(entity.getId())
                .date_of_loan(StringToDateConverter.convert(entity.getDate_of_loan()))
                .date_of_return(StringToDateConverter.convert(entity.getDate_of_return()))
                .book(toRespBook(entity.getBook()))
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
