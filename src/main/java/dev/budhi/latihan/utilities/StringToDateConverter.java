package dev.budhi.latihan.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class StringToDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT); // use package java.time
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT);

    static LocalDate localDate;
    static String text;

    @Override
    public LocalDate convert(String source) {
        try {

            localDate = LocalDate.parse(source, formatter1);
            // log.info("{}", localDate);
            return localDate;

        } catch (DateTimeParseException e) {
            log.warn("Error converting date from string: {}", source, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format is dd/MM/yyyy", e);
        }
    }

    public static String convert(LocalDate source) {
        try {

            localDate = source;
            if (Objects.isNull(localDate)){
                return null;
            }
            text = localDate.format(formatter2);
            //log.info("{}", text);
            return text;

        } catch (Exception e) {
            log.warn("Error convert data from string {}", source, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format is dd/MM/yyyy", e);
        }
    }

}
