package dev.budhi.latihan.utilities;

import dev.budhi.latihan.modules.loan.DTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConvertJson {

    public static void main(String[] args) throws JsonProcessingException {

        DTO.respAuthor author = DTO.respAuthor.builder()
                .name("author")
                .build();

        DTO.respGenre genre = DTO.respGenre.builder()
                .name("genre")
                .build();

        DTO.respBook book = DTO.respBook.builder()
                .title("book")
                .author(author)
                .genre(genre)
                .build();

        DTO.respMember member = DTO.respMember.builder()
                .name("member")
                .address("jl. mangga")
                .phone("1111122222")
                .build();

        DTO.respLoan load = DTO.respLoan.builder()
                .id(1L)
                .date_of_loan(StringToDateConverter.convert(LocalDate.now()))
                .date_of_return(StringToDateConverter.convert(LocalDate.now().withDayOfMonth(19)))
                .book(book)
                .member(member)
                .createdAt(LocalDateTime.now())
                .build();


//        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = objectWriter.writeValueAsString(load);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(load);


        System.out.println(json);

    }

}
