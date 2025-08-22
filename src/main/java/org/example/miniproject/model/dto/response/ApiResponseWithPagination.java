package org.example.miniproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseWithPagination <T>{
    private List<T> items;
    private PaginationResponse paginationResponse;


    public static <T> ApiResponseWithPagination<T> itemsAndPaginationResponse(Page<T> page) {
        return ApiResponseWithPagination.<T>builder()
                .items(page.getContent())
                .paginationResponse(PaginationResponse.paginationToResponse(page))
                .build();
    }


}
