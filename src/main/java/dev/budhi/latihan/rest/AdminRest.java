package dev.budhi.latihan.rest;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRest {

    @GetMapping
    public String get() {
        return "GET:: admin controller";
    }
    @PostMapping
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }

}
