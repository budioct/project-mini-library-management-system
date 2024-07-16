package dev.budhi.latihan.rest;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementRest {

    @GetMapping
    public String get() {
        return "GET:: management controller";
    }
    @PostMapping
    @Hidden
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    @Hidden
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    @Hidden
    public String delete() {
        return "DELETE:: management controller";
    }

}
