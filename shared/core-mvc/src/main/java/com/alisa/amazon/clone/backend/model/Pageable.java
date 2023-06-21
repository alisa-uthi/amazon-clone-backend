package com.alisa.amazon.clone.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pageable {
    @Schema(type = "int", example = "0", description = "Default value = 0")
    int pageNumber = 0;

    @Schema(type = "int", example = "10", description = "Default value = 10")
    int pageSize = 10;

    @Schema(type = "String", example = "ASC | DESC", description = "Default value = 'ASC'")
    String sortDirection = "ASC";

    @Schema(type = "List<String>", example = "['createdAt']")
    List<String> sortProperties;
}
