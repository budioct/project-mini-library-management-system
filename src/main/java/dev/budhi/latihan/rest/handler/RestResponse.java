package dev.budhi.latihan.rest.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class RestResponse {

    @Getter
    @Setter
    @Data
    @Builder
    public static class list<T>{
        private Integer status_code;
        private String message;
        private Integer count_data;
        private T data;
        private restPagingResponse paging;
        //private String errors;
    }

    @Getter
    @Setter
    @Data
    @Builder
    public static class object<T> {
        private Integer status_code;
        private String message;
        private T data;
        //private String errors;
    }

    @Getter
    @Setter
    @Data
    @Builder
    @Schema(description = "Response wrapper for errors")
    public static class restError<T> {
        @Schema(description = "Status code")
        private Integer status_code;
        @Schema(description = "Message")
        private String message;
        @Schema(description = "Errors")
        private String errors;
    }

    @Getter
    @Setter
    @Data
    @Builder
    public static class restPagingResponse {
        private Integer currentPage;
        private Integer totalPage;
        private Integer sizePage;

    }

}
